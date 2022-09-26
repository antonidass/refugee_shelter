package com.example.refugeeshelter.repositories;

import com.example.refugeeshelter.entities.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationsRepo extends JpaRepository<Reservations, Long> {
}
