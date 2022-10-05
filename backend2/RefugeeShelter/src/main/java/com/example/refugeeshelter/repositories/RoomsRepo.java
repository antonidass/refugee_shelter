package com.example.refugeeshelter.repositories;

import com.example.refugeeshelter.entities.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomsRepo extends JpaRepository<Rooms, Long> {
    Optional<List<Rooms>> findByUserId(Long ownerId);
}
