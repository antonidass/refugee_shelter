package com.example.refugeeshelter.service;

import com.example.refugeeshelter.entities.Reservations;
import com.example.refugeeshelter.repositories.ReservationsRepo;
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

    public List<Reservations> getReservations() {
        return reservationsRepo.findAll();
    }
}
