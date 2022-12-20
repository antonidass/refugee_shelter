package com.example.refugeeshelter.repositories;

import com.example.refugeeshelter.entities.Rooms;
import com.example.refugeeshelter.entities.User;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@Epic("Repository Test")
@Feature("Room Repository Test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RoomsRepoTest {
  @Autowired private RoomsRepo roomsRepo;
  @Autowired private UserRepo userRepo;

  @BeforeEach
  void setup() {
    Rooms rooms =
        Rooms.builder()
            .address("Address Test")
            .price(1234L)
            .beds(1L)
            .latitude(123D)
            .longitude(123D)
            .build();

    roomsRepo.save(rooms);
  }

  @Test
  @Description("User tries to save correct Room")
  void saveRoom() {
    Rooms rooms =
        Rooms.builder()
            .address("Address Test")
            .price(1234L)
            .beds(1L)
            .latitude(123D)
            .longitude(123D)
            .build();

    roomsRepo.save(rooms);

    Assertions.assertThat(rooms.getId()).isGreaterThan(0);
  }

  @Test
  @Description("User tries to get Room by ID")
  void getById() {
    Long id = 1L;

    Rooms rooms = roomsRepo.findById(id).orElse(null);

    Assertions.assertThat(rooms).isNotEqualTo(null);
    Assertions.assertThat(rooms.getId()).isEqualTo(id);
  }

  @Test
  @Description("User tries to get Room which not exists by ID")
  void getByIdNotExistRoom() {
    Long id = 1000L;

    Optional<Rooms> rooms = roomsRepo.findById(id);

    Assertions.assertThat(rooms).isNotPresent();
  }

  @Test
  @Description("User tries to get all Rooms")
  void getAllRooms() {
    List<Rooms> rooms = roomsRepo.findAll();

    Assertions.assertThat(rooms.size()).isEqualTo(1);
  }

  @Test
  @Description("User tries to get Owner's Rooms")
  void getRoomsByOwnerId() {
    User user = User.builder().username("testusername").password("test").build();
    userRepo.save(user);

    Rooms rooms2 =
        Rooms.builder()
            .address("Address Test 2")
            .price(53412L)
            .beds(1L)
            .latitude(321D)
            .longitude(321D)
            .user(user)
            .build();
    roomsRepo.save(rooms2);

    Optional<List<Rooms>> rooms = roomsRepo.findByUserId(user.getId());

    Assertions.assertThat(rooms).isPresent();
    Assertions.assertThat(rooms.get().get(0).getId()).isEqualTo(rooms2.getId());
  }

  @Test
  @Description("User tries to update Room")
  void updateRoom() {
    Long id = 1L;

    Optional<Rooms> rooms = roomsRepo.findById(id);

    Assertions.assertThat(rooms).isPresent();

    rooms.get().setPrice(999L);
    Rooms updatedRoom = roomsRepo.save(rooms.get());

    Assertions.assertThat(updatedRoom.getPrice()).isEqualTo(999L);
  }

  @Test
  @Description("User tries delete Room by ID")
  void deleteRoom() {
    Long id = 1L;

    Optional<Rooms> rooms = roomsRepo.findById(id);

    Assertions.assertThat(rooms).isPresent();

    roomsRepo.delete(rooms.get());

    Optional<Rooms> roomsDeleted = roomsRepo.findById(id);

    Assertions.assertThat(roomsDeleted).isNotPresent();
  }
}
