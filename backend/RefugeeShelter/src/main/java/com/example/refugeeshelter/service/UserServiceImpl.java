package com.example.refugeeshelter.service;

import com.example.refugeeshelter.dto.ApiResponse;
import com.example.refugeeshelter.dto.UserMapper;
import com.example.refugeeshelter.dto.response.RoomsResponse;
import com.example.refugeeshelter.dto.response.UserResponse;
import com.example.refugeeshelter.entities.Role;
import com.example.refugeeshelter.entities.Rooms;
import com.example.refugeeshelter.entities.User;
import com.example.refugeeshelter.exceptions.*;
import com.example.refugeeshelter.payload.UserPatchForm;
import com.example.refugeeshelter.repositories.RoleRepo;
import com.example.refugeeshelter.repositories.UserRepo;
import com.example.refugeeshelter.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserDetailsService {
  private final UserRepo userRepo;
  private final RoleRepo roleRepo;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  public ResponseEntity<?> getUsers() {
    log.info("Fetching all users from database");

    List<User> userList = userRepo.findAll();
    List<UserResponse> userResponses = new ArrayList<>(userList.size());

    userList.forEach(user -> userResponses.add(userMapper.toDto(user)));
    return ResponseEntity.ok().body(userResponses);
  }

  public User getUser(String username) {
    log.info("Fetching user {} from database", username);
    return userRepo.findByUsername(username);
  }

  public ResponseEntity<?> getUserById(Long id) {
    User user =
            userRepo
                    .findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot find user with", "id", id));
    return ResponseEntity.ok().body(userMapper.toDto(user));
  }

  public ResponseEntity<?> saveUser(User user) {
    log.info("Saving user {} to the database", user.getEmail());

    URI uri =
        URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/users/save")
                .toUriString());

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    return ResponseEntity.created(uri).body(userMapper.toDto(userRepo.save(user)));
  }

  public ResponseEntity<?> registerUser(User user) {
    URI uri =
        URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/register")
                .toUriString());


    if (userRepo.findByUsername(user.getUsername()) != null) {
      throw new LogicException();
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User savedUser = userRepo.save(user);
    // Добавляем роль юзеру
    UserPatchForm userPatchForm = new UserPatchForm();
    userPatchForm.setRole("ROLE_USER");
    addRoleToUser(savedUser.getId(), userPatchForm);


    return ResponseEntity.created(uri).body(userMapper.toDto(savedUser));
  }

  public Role saveRole(Role role) {
    log.info("Saving role {} to the database", role.getName());
    return roleRepo.save(role);
  }

  public ResponseEntity<?> updateUser(Long id, User newUser) {
    User user =
        userRepo
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cannot find user with", "id", id));

    user.setFields(newUser);
    return ResponseEntity.ok().body(userMapper.toDto(userRepo.save(user)));
  }

  // todo constraint room cannot delete
  public ResponseEntity<?> deleteUser(Long id) {

    User user =
        userRepo
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cannot find user with", "id", id));

    userRepo.delete(user);
    return ResponseEntity.ok().body(new ApiResponse(Boolean.TRUE, "User deleted successfully!"));
  }

  public User addRoleToUser(Long userId, UserPatchForm newUser) {
    log.info("Patch user {}", userId);
    User user =
        userRepo
            .findById(userId)
            .orElseThrow(() -> new FileStorageException("Cannot find user with id = " + userId));

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

    return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), authorities);
  }
}
