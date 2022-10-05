package com.example.refugeeshelter.repositories;


import com.example.refugeeshelter.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
}
