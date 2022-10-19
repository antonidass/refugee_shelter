package com.example.refugeeshelter.service;

import com.example.refugeeshelter.entities.Role;
import com.example.refugeeshelter.entities.User;
import com.example.refugeeshelter.exceptions.FileStorageException;
import com.example.refugeeshelter.payload.UserPatchForm;
import com.example.refugeeshelter.repositories.RoleRepo;
import com.example.refugeeshelter.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements  UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        log.info("Saving user {} to the database", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public Role saveRole(Role role) {
        log.info("Saving role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    public User addRoleToUser(Long userId, UserPatchForm newUser) {
        log.info("Patch user {}", userId);
        User user = userRepo.findById(userId).orElseThrow(() -> new FileStorageException("Cannot find user with id = " + userId));

        if (newUser.getUsername() != null) {
            user.setUsername(newUser.getUsername());
        }

        if (newUser.getName() != null) {
            user.setName(newUser.getName());
        }

        if (newUser.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }

        if (newUser.getEmail() != null) {
            user.setEmail(newUser.getEmail());
        }

        if (newUser.getPhone() != null) {
            user.setPhone(newUser.getPhone());
        }

        if (newUser.getRole() != null) {
            log.info("Role ={}", newUser.getRole());
            user.getRoles().add(roleRepo.findByName(newUser.getRole()));
        }
        return user;
    }

    public User getUser(String username) {
        log.info("Fetching user {} from database", username);
        return userRepo.findByUsername(username);
    }

    public List<User> getUsers() {
        log.info("Fetching all users from database");
        return userRepo.findAll();
    }

    public User updateUser(Long id, User newUser) {
        User user = userRepo.findById(id).orElseThrow(() -> new FileStorageException("Cannot find user with id = " + id));
        user.setFields(newUser);
        return user;
    }

    public User deleteUser(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new FileStorageException("Cannot find user with id = " + id));
        userRepo.delete(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }
        log.info("User found in the database {} {}", user.getUsername(), user.getPassword());


        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}