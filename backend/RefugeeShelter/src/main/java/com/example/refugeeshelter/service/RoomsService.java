package com.example.refugeeshelter.service;

import com.example.refugeeshelter.dto.ApiResponse;
import com.example.refugeeshelter.dto.RoomMapper;
import com.example.refugeeshelter.dto.response.RoomsResponse;
import com.example.refugeeshelter.entities.Rooms;
import com.example.refugeeshelter.entities.User;
import com.example.refugeeshelter.exceptions.ForbiddenException;
import com.example.refugeeshelter.exceptions.ResourceNotFoundException;
import com.example.refugeeshelter.exceptions.RoomsDeleteLogicException;
import com.example.refugeeshelter.filter.FilterRoomsObject;
import com.example.refugeeshelter.repositories.ReservationsRepo;
import com.example.refugeeshelter.repositories.RoomsRepo;
import com.example.refugeeshelter.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
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
public class RoomsService {
  private final RoomsRepo roomsRepo;
  private final UserRepo userRepo;
  private final RoomMapper roomMapper;
  private final ReservationsRepo reservationsRepo;

  public ResponseEntity<?> getRooms() {
    List<Rooms> roomsList = roomsRepo.findAll();
    List<RoomsResponse> roomsResponse = new ArrayList<>(roomsList.size());

    roomsList.forEach(room -> roomsResponse.add(roomMapper.toDto(room)));
    return ResponseEntity.ok().body(roomsResponse);
  }

  public ResponseEntity<?> getRoomById(Long id) {
    Rooms room =
        roomsRepo
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cannot find room with", "id", id));
    return ResponseEntity.ok().body(roomMapper.toDto(room));
  }

  public ResponseEntity<?> getRoomsByOwnerId(Long ownerId) {
    userRepo
        .findById(ownerId)
        .orElseThrow(
            () -> new ResourceNotFoundException("Cannot find user with id", "id", ownerId));

    List<Rooms> rooms = roomsRepo.findByUserId(ownerId).orElse(new ArrayList<>());
    List<RoomsResponse> roomsResponse = new ArrayList<>(rooms.size());
    rooms.forEach(room -> roomsResponse.add(roomMapper.toDto(room)));

    // Remove user field from response
    MappingJacksonValue filteredRooms =
        FilterRoomsObject.getInstance().getMappingJacksonValue(roomsResponse);

    return ResponseEntity.ok().body(filteredRooms);
  }

  public ResponseEntity<?> saveRoom(RoomsResponse roomsResponse) {
    // Get owner id through token TODO refactor this
    Long ownerId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

    User user =
        userRepo
            .findById(ownerId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Cannot find user with id", "id", ownerId));

    URI uri =
        URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rooms/{ownerId}")
                .toUriString());

    Rooms rooms = roomMapper.toRooms(roomsResponse);
    rooms.setUser(user);

    return ResponseEntity.created(uri).body(roomMapper.toDto(roomsRepo.save(rooms)));
  }

  public ResponseEntity<?> updateRoom(Long id, RoomsResponse newRoom) {
    Long ownerId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

    Rooms room =
        roomsRepo
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cannot find room with", "id", id));

    if (!room.getUser().getId().equals(ownerId)) {
      throw new ForbiddenException();
    }

    room.setFields(roomMapper.toRooms(newRoom));

    return ResponseEntity.ok().body(roomMapper.toDto(roomsRepo.save(room)));
  }

  public ResponseEntity<?> patchRoom(Long id, RoomsResponse newRoom) {
    Long ownerId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

    Rooms room =
        roomsRepo
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cannot find room with", "id", id));

    if (!room.getUser().getId().equals(ownerId)) {
      throw new ForbiddenException();
    }

    // TODO need something
    if (newRoom.getHasBathroom() != null) {
      room.setHasBathroom(newRoom.getHasBathroom());
    }

    if (newRoom.getHasKitchen() != null) {
      room.setHasKitchen(newRoom.getHasKitchen());
    }

    if (newRoom.getAddress() != null) {
      room.setAddress(newRoom.getAddress());
    }

    if (newRoom.getDescription() != null) {
      room.setDescription(newRoom.getDescription());
    }

    if (newRoom.getLatitude() != null) {
      room.setLatitude(newRoom.getLatitude());
    }

    if (newRoom.getLongitude() != null) {
      room.setLongitude(newRoom.getLongitude());
    }

    if (newRoom.getPrice() != null) {
      room.setPrice(newRoom.getPrice());
    }

    if (newRoom.getImageUrl() != null) {
      room.setImageUrl(newRoom.getImageUrl());
    }
    return ResponseEntity.ok().body(roomMapper.toDto(roomsRepo.save(room)));
  }

  public ResponseEntity<?> deleteRoom(Long id) {
    Long ownerId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

    Rooms room =
        roomsRepo
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cannot find room with", "id", id));

    if (!room.getUser().getId().equals(ownerId)) {
      throw new ForbiddenException();
    }

    // If room reserved we cannot delete it!
    if (reservationsRepo.findByRoomId(id).get().size() != 0) {
      throw new RoomsDeleteLogicException(id.toString());
    }

    roomsRepo.delete(room);
    return ResponseEntity.ok().body(new ApiResponse(Boolean.TRUE, "Room deleted successfully!"));
  }
}
