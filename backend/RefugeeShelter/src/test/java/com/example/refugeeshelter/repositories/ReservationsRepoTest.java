package com.example.refugeeshelter.repositories;

import com.example.refugeeshelter.entities.Reservations;
import com.example.refugeeshelter.entities.Rooms;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@Epic("Repository Test")
@Feature("Reservations Repository Test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReservationsRepoTest {
  @Autowired private ReservationsRepo reservationsRepo;
  @Autowired private RoomsRepo roomsRepo;
  @Autowired private UserRepo userRepo;

  @SneakyThrows
  @BeforeEach
  void setup() {
    User user = User.builder().username("testusername").password("test").build();
    User userOwner = User.builder().username("ownername").password("owner").build();

    userRepo.save(user);
    userRepo.save(userOwner);

    Rooms rooms =
        Rooms.builder()
            .address("Address Test 1")
            .price(53412L)
            .beds(1L)
            .latitude(321D)
            .longitude(321D)
            .user(userOwner)
            .build();
    roomsRepo.save(rooms);

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Reservations reservations =
        Reservations.builder()
            .startDate(df.parse("2022-01-02"))
            .endDate(df.parse("2022-02-02"))
            .user(userRepo.findByUsername("testusername"))
            .room(roomsRepo.findById(1L).orElse(null))
            .build();
    reservationsRepo.save(reservations);
  }

  @SneakyThrows
  @Test
  @Description("User tries to save correct Reservation")
  void saveReservation() {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Reservations reservations =
        Reservations.builder()
            .startDate(df.parse("2022-01-02"))
            .endDate(df.parse("2022-02-02"))
            .user(userRepo.findByUsername("testusername"))
            .room(roomsRepo.findById(1L).orElse(null))
            .build();

    reservationsRepo.save(reservations);

    Assertions.assertThat(reservations.getId()).isGreaterThan(0);
  }

  @Test
  @Description("User tries to get Reservation by ID")
  void getById() {
    Long id = 1L;

    Reservations reservations = reservationsRepo.findById(id).orElse(null);

    Assertions.assertThat(reservations).isNotEqualTo(null);
    Assertions.assertThat(reservations.getId()).isEqualTo(id);
  }

  @Test
  @Description("User tries to get Reservation which not exists by ID")
  void getByIdNotExistReservation() {
    Long id = 1000L;

    Optional<Reservations> reservations = reservationsRepo.findById(id);

    Assertions.assertThat(reservations).isNotPresent();
  }

  @Test
  @Description("User tries to get all Reservations")
  void getAllReservations() {
    List<Reservations> reservations = reservationsRepo.findAll();

    Assertions.assertThat(reservations.size()).isEqualTo(1);
  }

  @Test
  @Description("User tries to get Owner's Reservations")
  void getReservationsByOwnerId() {
    Long ownerId = 2L;

    List<Reservations> reservations = reservationsRepo.findByRoomUserId(ownerId).orElse(null);

    Assertions.assertThat(reservations).isNotEqualTo(null);
    Assertions.assertThat(reservations.get(0).getId()).isEqualTo(1L);
  }

  @SneakyThrows
  @Test
  @Description("User tries to update Reservation")
  void updateReservation() {
    Long id = 1L;

    Optional<Reservations> reservations = reservationsRepo.findById(id);

    Assertions.assertThat(reservations).isPresent();

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    reservations.get().setEndDate(df.parse("2022-03-03"));
    Reservations updatedRes = reservationsRepo.save(reservations.get());

    Assertions.assertThat(updatedRes.getEndDate()).isEqualTo(df.parse("2022-03-03"));
  }

  @Test
  @Description("User tries delete Reservation by ID")
  void deleteReservation() {
    Long id = 1L;

    Optional<Reservations> reservations = reservationsRepo.findById(id);

    Assertions.assertThat(reservations).isPresent();

    reservationsRepo.delete(reservations.get());

    Optional<Reservations> reservationsDeleted = reservationsRepo.findById(id);

    Assertions.assertThat(reservationsDeleted).isNotPresent();
  }
}
