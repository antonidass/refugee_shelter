package com.example.refugeeshelter.service;

import com.example.refugeeshelter.dto.ApiResponse;
import com.example.refugeeshelter.dto.ReservationMapper;
import com.example.refugeeshelter.dto.request.ReservationRequest;
import com.example.refugeeshelter.dto.response.ReservationResponse;
import com.example.refugeeshelter.entities.Reservations;
import com.example.refugeeshelter.entities.Rooms;
import com.example.refugeeshelter.entities.User;
import com.example.refugeeshelter.exceptions.*;
import com.example.refugeeshelter.repositories.ReservationsRepo;
import com.example.refugeeshelter.repositories.RoomsRepo;
import com.example.refugeeshelter.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReservationsService {
  private final ReservationsRepo reservationsRepo;
  private final UserRepo userRepo;
  private final RoomsRepo roomsRepo;
  private final ReservationMapper reservationMapper;

  public ResponseEntity<?> getReservations() {
    List<Reservations> reservationsList = reservationsRepo.findAll();
    List<ReservationResponse> reservationResponses = new ArrayList<>(reservationsList.size());

    reservationsList.forEach(res -> reservationResponses.add(reservationMapper.toDto(res)));
    return ResponseEntity.ok().body(reservationResponses);
  }

  public ResponseEntity<?> getReservationsById(Long id) {
    Reservations reservations =
        reservationsRepo
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Cannot find reservation with", "id", id));
    return ResponseEntity.ok().body(reservationMapper.toDto(reservations));
  }

  public ResponseEntity<?> getReservationsByOwnerId(Long ownerId) {
    userRepo
        .findById(ownerId)
        .orElseThrow(
            () -> new ResourceNotFoundException("Cannot find user with id", "id", ownerId));

    List<Reservations> reservations =
        reservationsRepo.findByRoomUserId(ownerId).orElse(new ArrayList<>());

    List<ReservationResponse> reservationResponses = new ArrayList<>(reservations.size());
    reservations.forEach(res -> reservationResponses.add(reservationMapper.toDto(res, true)));

    return ResponseEntity.ok().body(reservationResponses);
  }

  public ResponseEntity<?> saveReservation(ReservationRequest reservationRequest) {
    if (reservationRequest.getRoomId() == null) {
      throw new MissedRequestDataException();
    }

    // Get owner id through token TODO refactor this
    Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

    User user =
        userRepo
            .findById(userId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Cannot find user with id", "id", userId));

    Rooms room =
        roomsRepo
            .findById(reservationRequest.getRoomId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Cannot find room with", "id", reservationRequest.getRoomId()));

    URI uri = URI.create("");

    // Check dates for correct  TODO to validation layer
    if (reservationRequest.getStartDate().after(reservationRequest.getEndDate())) {
      throw new LogicException();
    }

    // Check if dates not available
    List<Reservations> reservationsCheckList =
        reservationsRepo.findByRoomId(reservationRequest.getRoomId()).get();
    reservationsCheckList.forEach(
        reservations -> {
          if ((reservationRequest.getStartDate().before(reservations.getStartDate())
                  && reservationRequest.getEndDate().before(reservations.getStartDate()))
              || (reservationRequest.getStartDate().after(reservations.getEndDate()))) {
          } else {
            // TODO check exceptions all refactor
            throw new LogicException();
          }
        });

    Reservations reservations =
        new Reservations(reservationRequest.getStartDate(), reservationRequest.getEndDate());
    reservations.setUser(user);
    reservations.setRoom(room);

    return ResponseEntity.created(uri)
        .body(reservationMapper.toDto(reservationsRepo.save(reservations)));
  }

  public ResponseEntity<?> updateReservation(Long id, ReservationRequest newReservation) {
    Long ownerId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
    Reservations reservations =
        reservationsRepo
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Cannot find reservation with", "id", id));

    if (!reservations.getUser().getId().equals(ownerId)) {
      throw new ForbiddenException();
    }

    // Check dates for correct  TODO to validation layer
    if (newReservation.getStartDate().after(newReservation.getEndDate())) {
      throw new LogicException();
    }
    // Check if dates not available
    List<Reservations> reservationsCheckList =
        reservationsRepo.findByRoomId(newReservation.getRoomId()).get();
    reservationsCheckList.forEach(
        res -> {
          if ((newReservation.getStartDate().before(res.getStartDate())
                  && newReservation.getEndDate().before(res.getStartDate()))
              || (newReservation.getStartDate().after(res.getEndDate()))) {
          } else {
            // TODO check exceptions all refactor
            throw new LogicException();
          }
        });

    reservations.setFields(
        new Reservations(newReservation.getStartDate(), newReservation.getEndDate()));
    return ResponseEntity.ok().body(reservationMapper.toDto(reservationsRepo.save(reservations)));
  }

  public ResponseEntity<?> deleteReservation(Long id) {
    Long ownerId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

    Reservations reservations =
        reservationsRepo
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Cannot find reservation with", "id", id));

    if (!reservations.getUser().getId().equals(ownerId)) {
      throw new ForbiddenException();
    }

    reservationsRepo.delete(reservations);
    return ResponseEntity.ok()
        .body(new ApiResponse(Boolean.TRUE, "Reservation deleted successfully!"));
  }
}
