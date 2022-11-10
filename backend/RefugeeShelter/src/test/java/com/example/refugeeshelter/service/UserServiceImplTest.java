package com.example.refugeeshelter.service;

import com.example.refugeeshelter.dto.UserMapper;
import com.example.refugeeshelter.dto.response.UserDTO;
import com.example.refugeeshelter.entities.Role;
import com.example.refugeeshelter.entities.User;
import com.example.refugeeshelter.payload.UserPatchForm;
import com.example.refugeeshelter.repositories.RoleRepo;
import com.example.refugeeshelter.repositories.UserRepo;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@Epic("Service Test")
@Feature("User Service Test")
@Slf4j
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
  @Mock private UserRepo userRepo;
  @Mock private RoleRepo roleRepo;

  @Mock private UserMapper userMapper;

  @Mock private PasswordEncoder passwordEncoder;

  @InjectMocks private UserServiceImpl userService;

  @Test
  @Description("Get Users")
  void getUsers() {
    final User user1 = mock(User.class);
    final User user2 = mock(User.class);
    when(userRepo.findAll()).thenReturn(Arrays.asList(user1, user2));

    final ResponseEntity<?> userRes = userService.getUsers();

    Assertions.assertThat(userRes.getBody())
        .isEqualTo(Arrays.asList(userMapper.toDto(user1), userMapper.toDto(user2)));
    verify(userRepo).findAll();
  }

  @Test
  @Description("Get User By Id")
  void getUserById() {
    final User user = mock(User.class);
    Long id = 1L;
    when(userRepo.findById(id)).thenReturn(java.util.Optional.ofNullable(user));

    final ResponseEntity<?> userRes = userService.getUserById(id);

    Assertions.assertThat(userRes.getBody())
        .isEqualTo(ResponseEntity.ok().body(userMapper.toDto(user)).getBody());
    verify(userRepo).findById(id);
  }

  @Test
  @Description("Get User By UserName")
  void getUserByUsername() {
    final User user = mock(User.class);
    String username = "testuser";
    when(userRepo.findByUsername(username)).thenReturn(user);

    final User userRes = userService.getUser(username);

    Assertions.assertThat(userRes).isEqualTo(user);
    verify(userRepo).findByUsername(username);
  }

  @Test
  @Description("Tries to save User")
  void saveUser() {
    final User user = mock(User.class);
    final UserDTO userDTO = mock(UserDTO.class);
    when(userRepo.save(user)).thenReturn(user);
    when(userMapper.toDto(user)).thenReturn(userDTO);
    when(passwordEncoder.encode(user.getPassword())).thenReturn("hashed");

    final ResponseEntity<?> userRes = userService.saveUser(user);

    Assertions.assertThat((UserDTO) userRes.getBody()).isEqualTo(userDTO);
    verify(userRepo).save(user);
  }

  @Test
  @Description("Tries to register User")
  void registerUser() {
    User user =
        User.builder()
            .username("testusername")
            .password("test")
            .name("testname")
            .roles(new ArrayList<>())
            .id(1L)
            .build();
    final UserDTO userDTO = userMapper.toDto(user);
    when(userRepo.findByUsername(user.getUsername())).thenReturn(null);
    when(userRepo.save(user)).thenReturn(user);
    when(userMapper.toDto(user)).thenReturn(userDTO);
    when(passwordEncoder.encode(user.getPassword())).thenReturn("hashed");
    when(userRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));
    when(roleRepo.findByName("ROLE_USER")).thenReturn(Role.builder().name("ROLE_USER").build());

    final ResponseEntity<?> userRes = userService.registerUser(user);

    Assertions.assertThat((UserDTO) userRes.getBody()).isEqualTo(userDTO);
    verify(userRepo).save(user);
    verify(userRepo).findByUsername(user.getUsername());
  }

  @Test
  @Description("Tries to save Role")
  void saveRole() {
    final Role role = mock(Role.class);
    when(roleRepo.save(role)).thenReturn(role);

    Role roleRes = userService.saveRole(role);

    Assertions.assertThat(roleRes).isEqualTo(role);
    verify(roleRepo).save(role);
  }

  @Test
  @Description("Tries to update User")
  void updateUser() {
    User user =
        User.builder()
            .username("testusername")
            .password("test")
            .name("testname")
            .roles(new ArrayList<>())
            .id(1L)
            .build();
    User newUser =
        User.builder()
            .username("new")
            .password("new")
            .name("new")
            .roles(new ArrayList<>())
            .id(1L)
            .build();
    final UserDTO userDTO = userMapper.toDto(newUser);
    when(userRepo.save(user)).thenReturn(user);
    when(userMapper.toDto(user)).thenReturn(userDTO);
    when(userRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));

    final ResponseEntity<?> userRes = userService.updateUser(1L, newUser);

    Assertions.assertThat((UserDTO) userRes.getBody()).isEqualTo(userDTO);
    verify(userRepo).save(user);
  }

  @Test
  @Description("Tries to delete User")
  void deleteUser() {
    User user =
        User.builder()
            .username("testusername")
            .password("test")
            .name("testname")
            .roles(new ArrayList<>())
            .id(1L)
            .build();
    when(userRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));

    final ResponseEntity<?> userRes = userService.deleteUser(1L);

    Assertions.assertThat(userRes.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(userRepo).delete(user);
  }

  @Test
  @Description("Tries to change email of User")
  void changeEmailUser() {
    User user =
        User.builder()
            .username("testusername")
            .password("test")
            .name("testname")
            .roles(new ArrayList<>())
            .id(1L)
            .build();
    UserPatchForm userPatchForm = new UserPatchForm();
    userPatchForm.setEmail("new@gmail.com");
    when(userRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(user));

    final User userRes = userService.addRoleToUser(1L, userPatchForm);

    Assertions.assertThat(userRes.getEmail()).isEqualTo("new@gmail.com");
    verify(userRepo).findById(1L);
  }

  @Test
  @Description("Tries to load User By Username")
  void loadByUsername() {
    String username = "testusername";
    User user =
        User.builder()
            .username(username)
            .password("test")
            .name("testname")
            .roles(new ArrayList<>())
            .id(1L)
            .build();
    when(userRepo.findByUsername(username)).thenReturn(user);

    final UserDetails userRes = userService.loadUserByUsername(username);

    Assertions.assertThat(userRes.getUsername()).isEqualTo(username);
    verify(userRepo).findByUsername(username);
  }
}
