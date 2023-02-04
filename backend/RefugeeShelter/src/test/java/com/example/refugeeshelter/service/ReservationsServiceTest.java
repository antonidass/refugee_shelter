package com.example.refugeeshelter.service;

import com.example.refugeeshelter.dto.ReservationMapper;
import com.example.refugeeshelter.dto.UserMapper;
import com.example.refugeeshelter.dto.request.ReservationRequest;
import com.example.refugeeshelter.dto.response.ReservationResponse;
import com.example.refugeeshelter.entities.Reservations;
import com.example.refugeeshelter.entities.Rooms;
import com.example.refugeeshelter.entities.User;
import com.example.refugeeshelter.exceptions.LogicException;
import com.example.refugeeshelter.repositories.ReservationsRepo;
import com.example.refugeeshelter.repositories.RoomsRepo;
import com.example.refugeeshelter.repositories.UserRepo;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@Epic("Service Test")
@Feature("Reservations Service Test")
@Slf4j
@ExtendWith(MockitoExtension.class)
class ReservationsServiceTest {
  @Mock private UserRepo userRepo;
  @Mock private RoomsRepo roomsRepo;

  @Mock private ReservationsRepo reservationsRepo;

  @Mock private UserMapper userMapper;
  @Mock private ReservationMapper reservationMapper;

  @InjectMocks private ReservationsService reservationsService;

  @Test
  @Description("Get Reservations")
  void getReservations() {
    final Reservations res1 = mock(Reservations.class);
    final Reservations res2 = mock(Reservations.class);
    when(reservationsRepo.findAll()).thenReturn(Arrays.asList(res1, res2));

    final ResponseEntity<?> reservationsRes = reservationsService.getReservations();

    Assertions.assertThat(reservationsRes.getBody())
        .isEqualTo(Arrays.asList(reservationMapper.toDto(res1), reservationMapper.toDto(res2)));
    verify(reservationsRepo).findAll();
  }

  @Test
  @Description("Get Reservation By Id")
  void getReservationById() {
    final Reservations reservations = mock(Reservations.class);
    final ReservationResponse resDto = mock(ReservationResponse.class);
    Long id = 1L;
    when(reservationsRepo.findById(id)).thenReturn(java.util.Optional.ofNullable(reservations));
    when(reservationMapper.toDto(reservations)).thenReturn(resDto);

    final ResponseEntity<?> reservationsRes = reservationsService.getReservationsById(id);

    Assertions.assertThat(reservationsRes.getBody()).isEqualTo(resDto);
    verify(reservationsRepo).findById(id);
  }

  @Test
  @Description("Get Reservation By Owner Id")
  void getReservationByOwnerId() {
    User user = User.builder().username("testusername").build();
    List<Reservations> reservationsList = new ArrayList<>();
    List<ReservationResponse> reservationResponses = new ArrayList<>();
    Long id = 1L;
    when(reservationsRepo.findByRoomUserId(id)).thenReturn(java.util.Optional.of(reservationsList));
    when(userRepo.findById(id)).thenReturn(java.util.Optional.ofNullable(user));

    final ResponseEntity<?> reservationsRes = reservationsService.getReservationsByOwnerId(id);

    Assertions.assertThat(reservationsRes.getBody()).isEqualTo(reservationResponses);
    verify(userRepo).findById(id);
    verify(reservationsRepo).findByRoomUserId(id);
  }

  @SneakyThrows
//  @Test
  @Description("User tries to save Reservation")
  void saveReservation() {
    Rooms room = Rooms.builder().latitude(123.1).longitude(321.1).price(12300L).id(1L).build();
    User user = User.builder().username("testusername").password("test").name("testname").build();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    ReservationRequest reservationRequest =
        ReservationRequest.builder()
//            .startDate(df.parse("2022-03-02"))
//            .endDate(df.parse("2022-02-02"))
            .roomId(1L)
            .build();
    Long ownerId = 1L;
    final Authentication authentication = mock(Authentication.class);
    final SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getName()).thenReturn(String.valueOf(ownerId));
    SecurityContextHolder.setContext(securityContext);
    when(userRepo.findById(ownerId)).thenReturn(java.util.Optional.ofNullable(user));
    when(roomsRepo.findById(1L)).thenReturn(java.util.Optional.ofNullable(room));
    boolean isThrow = false;
    try {
      final ResponseEntity<?> reservation = reservationsService.saveReservation(reservationRequest);
    } catch (LogicException e) {
      isThrow = true;
    }
    Assertions.assertThat(isThrow).isEqualTo(true);
  }

  @SneakyThrows
  @Test
  @Description("User tries to update Reservation")
  void updateReservation() {
    Rooms room = Rooms.builder().latitude(123.1).longitude(321.1).price(12300L).id(1L).build();
    User user =
        User.builder().username("testusername").password("test").name("testname").id(1L).build();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Reservations reservations =
        Reservations.builder()
            .startDate(df.parse("2022-01-01"))
            .endDate(df.parse("2022-02-02"))
            .user(user)
            .room(room)
            .build();

    ReservationRequest reservationRequest =
        ReservationRequest.builder()
//            .startDate(df.parse("2022-03-02"))
//            .endDate(df.parse("2022-05-02"))
            .roomId(1L)
            .build();

    ReservationResponse reservationResponse =
            ReservationResponse.builder()
                    .startDate(df.parse("2022-03-02"))
                    .endDate(df.parse("2022-05-02"))
                    .build();

    Long ownerId = 1L;
    List<Reservations> reservationsList = new ArrayList<>();
    final Authentication authentication = mock(Authentication.class);
    final SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getName()).thenReturn(String.valueOf(ownerId));
    SecurityContextHolder.setContext(securityContext);

    when(reservationsRepo.findById(ownerId))
        .thenReturn(java.util.Optional.ofNullable(reservations));
    when(reservationsRepo.findByRoomId(1L)).thenReturn(java.util.Optional.of(reservationsList));
    when(reservationsRepo.save(reservations)).thenReturn(reservations);
    when(reservationMapper.toDto(reservations)).thenReturn(reservationResponse);

    final ResponseEntity<?> reservation =
        reservationsService.updateReservation(1L, reservationRequest);

    Assertions.assertThat(reservation.getBody()).isEqualTo(reservationResponse);
    verify(reservationsRepo).findByRoomId(1L);
    verify(reservationsRepo).save(reservations);
  }

  @SneakyThrows
  @Test
  @Description("User tries to delete Reservation")
  void deleteReservations() {
    User user =
            User.builder().username("testusername").password("test").name("testname").id(1L).build();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Reservations reservations =
            Reservations.builder()
                    .startDate(df.parse("2022-01-01"))
                    .endDate(df.parse("2022-02-02"))
                    .user(user)
                    .build();
    Long ownerId = 1L;
    final Authentication authentication = mock(Authentication.class);
    final SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getName()).thenReturn(String.valueOf(ownerId));
    SecurityContextHolder.setContext(securityContext);
    when(reservationsRepo.findById(ownerId))
            .thenReturn(java.util.Optional.ofNullable(reservations));

    final ResponseEntity<?> reservation =
            reservationsService.deleteReservation(1L);

    Assertions.assertThat(reservation.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(reservationsRepo).findById(1L);
    verify(reservationsRepo).delete(reservations);
  }


}
