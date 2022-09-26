package com.example.refugeeshelter.controllers;

import com.example.refugeeshelter.entities.Reservations;
import com.example.refugeeshelter.filter.FilteredReservationsObjectMapper;
import com.example.refugeeshelter.service.ReservationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReservationsController {
    private final ReservationsService reservationsService;

    @GetMapping("/reservations")
    public ResponseEntity<?> getReservations() {
        List<Reservations> reservationsList = reservationsService.getReservations();

        // Delete user field from rooms object
//        MappingJacksonValue filteredReservationList = FilteredReservationsObjectMapper.getInstance(reservationsList).getMappingJacksonValue();
    return ResponseEntity.ok().body("s");
//        return ResponseEntity.ok().body(filteredReservationList);
    }
}
