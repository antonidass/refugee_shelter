package com.example.refugeeshelter.service;

import com.example.refugeeshelter.entities.Role;
import com.example.refugeeshelter.entities.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String role);
    User getUser(String username);
    List<User> getUsers();
}
