package com.example.refugeeshelter.controllers;

import com.example.refugeeshelter.entities.Reservations;
import com.example.refugeeshelter.exceptions.FileStorageException;
import com.example.refugeeshelter.filter.FilteredReservationsObjectMapper;
import com.example.refugeeshelter.service.ReservationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class ReservationsController {
    private final ReservationsService reservationsService;

    @GetMapping("/reservations")
    public ResponseEntity<?> getReservations() {
        List<Reservations> reservationsList = reservationsService.getReservations();
        return ResponseEntity.ok().body(reservationsList);
    }

    @GetMapping("/reservations/user/{ownerId}")
    public ResponseEntity<?> getReservationsByOwnerId(@PathVariable Long ownerId) {
        List<Reservations> reservations;
        try {
            reservations = reservationsService.getReservationsByOwnerId(ownerId);
        } catch (FileStorageException e) {
            return ResponseEntity.badRequest().body("Reservation with owner id = " + ownerId + " not founded!");
        }
        MappingJacksonValue filteredReservations = FilteredReservationsObjectMapper.getInstance().getMappingJacksonValue(reservations);
        return ResponseEntity.ok().body(filteredReservations);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<?> getReservationsById(@PathVariable Long id) {
        Reservations reservations;
        try {
            reservations = reservationsService.getReservationsById(id);
        } catch (FileStorageException e) {
            return ResponseEntity.badRequest().body("Reservation with id = " + id + " not founded!");
        }
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations/user/{userId}/rooms/{roomId}")
    public ResponseEntity<?> saveReservation(@PathVariable Long userId, @PathVariable Long roomId, @RequestBody Reservations newReservation) {
        Reservations reservation;
        try {
            reservation = reservationsService.saveReservation(userId, roomId, newReservation);
        } catch (FileStorageException e) {
            return ResponseEntity.badRequest().body("Cannot find  with id = " + userId + " not founded!"); // TODO
        }
        return ResponseEntity.ok().body(reservation);
    }

    @PutMapping("/reservations/{id}")
    public ResponseEntity<?> updateReservationById(@PathVariable Long id, @RequestBody Reservations newReservation) {
        Reservations reservation;
        try {
            reservation = reservationsService.updateReservation(id, newReservation);
        } catch (FileStorageException e) {
            return ResponseEntity.badRequest().body("Reservation with id = " + id + " not founded!");
        }
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<?> deleteReservationById(@PathVariable Long id) {
        Reservations reservation;
        try {
            reservation = reservationsService.deleteReservation(id);
        } catch (FileStorageException e) {
            return ResponseEntity.badRequest().body("Reservation with id = " + id + " not founded!");
        }
        return ResponseEntity.ok().body(reservation);
    }
}
