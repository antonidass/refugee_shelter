package com.example.refugeeshelter.repositories;

import com.example.refugeeshelter.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
