package com.example.refugeeshelter.repositories;


import com.example.refugeeshelter.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
