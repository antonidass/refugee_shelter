package com.example.refugeeshelter.service;

import com.example.refugeeshelter.entities.Reservations;
import com.example.refugeeshelter.entities.Reservations;
import com.example.refugeeshelter.entities.Rooms;
import com.example.refugeeshelter.entities.User;
import com.example.refugeeshelter.exceptions.FileStorageException;
import com.example.refugeeshelter.repositories.ReservationsRepo;
import com.example.refugeeshelter.repositories.RoomsRepo;
import com.example.refugeeshelter.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReservationsService {
    private final ReservationsRepo reservationsRepo;
    private final UserRepo userRepo;
    private final RoomsRepo roomsRepo;

    public List<Reservations> getReservations() {
        return reservationsRepo.findAll();
    }

    public Reservations getReservationsById(Long id) {
        return reservationsRepo.findById(id).orElseThrow(() -> new FileStorageException("Cannot find reservation with id = " + id));
    }

    public List<Reservations> getReservationsByOwnerId(Long ownerId) {
        return reservationsRepo.findByRoomUserId(ownerId).orElseThrow(() -> new FileStorageException("Cannot find reservation with owner id = " + ownerId));
    }

    public Reservations saveReservation(Long userId, Long roomId, Reservations reservations) {
        User user = userRepo.findById(userId).orElseThrow(() -> new FileStorageException("Cannot find user with id = " + userId));
        Rooms rooms = roomsRepo.findById(roomId).orElseThrow(() -> new FileStorageException("Cannot find room with id = " + roomId));

        reservations.setUser(user);
        reservations.setRoom(rooms);

        return reservationsRepo.save(reservations);
    }

    public Reservations updateReservation(Long id, Reservations newReservation)  {
        Reservations reservation = reservationsRepo.findById(id).orElseThrow(() -> new FileStorageException("Cannot find reservation with id = " + id));

        reservation.setFields(newReservation);
        return reservationsRepo.save(reservation);
    }

    public Reservations deleteReservation(Long id)  {
        Reservations reservation = reservationsRepo.findById(id).orElseThrow(() -> new FileStorageException("Cannot find reservation with id = " + id));
        reservationsRepo.deleteById(id);
        return reservation;
    }
}
