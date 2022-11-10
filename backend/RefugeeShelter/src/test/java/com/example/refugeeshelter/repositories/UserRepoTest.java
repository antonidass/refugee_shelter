package com.example.refugeeshelter.repositories;

import com.example.refugeeshelter.entities.User;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Epic("Repository Test")
@Feature("User Repository Test")
class UserRepoTest {
  @Autowired private UserRepo userRepo;

  @SneakyThrows
  @BeforeEach
  void setup() {
    User user = User.builder().username("testusername").password("test").build();
    userRepo.save(user);
  }

  @SneakyThrows
  @Test
  @Description("Tries to save correct User")
  void saveUser() {
    User user =
        User.builder()
            .username("user")
            .password("password")
            .email("user@gmail.com")
            .phone("89061321111")
            .build();

    userRepo.save(user);

    Assertions.assertThat(user.getId()).isGreaterThan(0);
  }

  @Test
  @Description("Get User By Username")
  void getUserByUsername() {
    String username = "testusername";

    User user = userRepo.findByUsername(username);

    Assertions.assertThat(user.getUsername()).isEqualTo(username);
  }

  @Test
  @Description("Get User By Id")
  void getUserById() {
    Long id = 1L;

    User user = userRepo.findById(id).orElse(null);

    Assertions.assertThat(user).isNotEqualTo(null);
    Assertions.assertThat(user.getId()).isEqualTo(id);
  }

  @Test
  @Description("Tries to get User whose not exists by Id")
  void getUserByIdWhoseNotExist() {
    Long id = 1000L;

    Optional<User> user = userRepo.findById(id);

    Assertions.assertThat(user).isNotPresent();
  }

  @Test
  @Description("Tries to get all Users")
  void getAllUsers() {
    List<User> users = userRepo.findAll();

    Assertions.assertThat(users.size()).isEqualTo(1);
  }

  @Test
  @Description("Tries to update User")
  void updateUser() {
    Long id = 1L;
    String newEmail = "newemail@gmail.com";

    Optional<User> user = userRepo.findById(id);

    Assertions.assertThat(user).isPresent();

    user.get().setEmail(newEmail);
    User updatedUser = userRepo.save(user.get());

    Assertions.assertThat(updatedUser.getEmail()).isEqualTo(newEmail);
  }

  @Test
  @Description("Tries delete User by ID")
  void deleteUser() {
    Long id = 1L;

    Optional<User> user = userRepo.findById(id);

    Assertions.assertThat(user).isPresent();

    userRepo.delete(user.get());

    Optional<User> userDeleted = userRepo.findById(id);

    Assertions.assertThat(userDeleted).isNotPresent();
  }
}
