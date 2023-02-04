package com.example.refugeeshelter.controllers;

import com.example.refugeeshelter.dto.request.ReservationRequest;
import com.example.refugeeshelter.dto.response.ReservationResponse;
import com.example.refugeeshelter.service.ReservationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class ReservationsController {
  private final ReservationsService reservationsService;

  @GetMapping("/reservations")
  public ResponseEntity<?> getReservations() {
    return reservationsService.getReservations();
  }

  @GetMapping("/reservations/{id}")
  public ResponseEntity<?> getReservationsById(@PathVariable Long id) {
    return reservationsService.getReservationsById(id);
  }

  @GetMapping("/rooms/{id}/reservations")
  public ResponseEntity<?> getReservationsByRoomId(@PathVariable Long id) {
    return reservationsService.getReservationsByRoomId(id);
  }

  @GetMapping("/users/{ownerId}/reservations")
  public ResponseEntity<?> getReservationsByOwnerId(@PathVariable Long ownerId) {
    return reservationsService.getReservationsByOwnerId(ownerId);
  }

  @PostMapping("/reservations")
  public ResponseEntity<?> saveReservation(@RequestBody ReservationRequest newReservation) {
    return reservationsService.saveReservation(newReservation);
  }

  @PutMapping("/reservations/{id}")
  public ResponseEntity<?> updateReservationById(
      @PathVariable Long id, @RequestBody ReservationRequest newReservation) {
    return reservationsService.updateReservation(id, newReservation);
  }

  @DeleteMapping("/reservations/{id}")
  public ResponseEntity<?> deleteReservationById(@PathVariable Long id) {
    return reservationsService.deleteReservation(id);
  }
}
