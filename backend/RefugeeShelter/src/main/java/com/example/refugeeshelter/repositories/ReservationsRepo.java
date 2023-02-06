package com.example.refugeeshelter.repositories;

import com.example.refugeeshelter.entities.Reservations;
import com.example.refugeeshelter.entities.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationsRepo extends JpaRepository<Reservations, Long> {
    Optional<List<Reservations>> findByRoomUserId(Long ownerId);
    Optional<List<Reservations>> findByRoomId(Long roomId);
    Optional<List<Reservations>> findByUserId(Long id);

}
