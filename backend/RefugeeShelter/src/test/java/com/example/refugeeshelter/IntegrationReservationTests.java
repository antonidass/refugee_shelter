package com.example.refugeeshelter;

import com.example.refugeeshelter.dto.request.ReservationRequest;
import com.example.refugeeshelter.dto.response.ReservationResponse;
import com.example.refugeeshelter.entities.Reservations;
import com.example.refugeeshelter.entities.Rooms;
import com.example.refugeeshelter.entities.User;
import com.example.refugeeshelter.service.ReservationsService;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.SneakyThrows;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;

@Epic("Integration Test")
@Feature("Integration Reservation Test")
@ActiveProfiles("test")
@SpringBootTest
public class IntegrationReservationTests {
  @Autowired private ReservationsService reservationsService;

  private String sqlUrl = "jdbc:postgresql://localhost:5433/ref_shel";

  @SneakyThrows
  @BeforeEach
  public void setup() {
    DriverManager.registerDriver(new Driver());
    Connection connection = DriverManager.getConnection(sqlUrl, "akrik", "akrik");
    ScriptRunner sr = new ScriptRunner(connection);
    Reader reader =
        new BufferedReader(new FileReader("src/test/resources/setup_scripts/create.sql"));
    sr.runScript(reader);
    reader = new BufferedReader(new FileReader("src/test/resources/setup_scripts/insert.sql"));
    sr.runScript(reader);
  }

  @AfterEach
  @SneakyThrows
  public void clean() {
    DriverManager.registerDriver(new Driver());
    Connection connection = DriverManager.getConnection(sqlUrl, "akrik", "akrik");
    ScriptRunner sr = new ScriptRunner(connection);
    Reader reader = new BufferedReader(new FileReader("src/test/resources/setup_scripts/drop.sql"));
    sr.runScript(reader);
  }

  @Test
  @Description("User tries to get Reservation")
  public void getReservationByIdShouldReturnRoom() {
    // Arrange
    Reservations expectedReservation =
        new Reservations(
            1L,
            new Timestamp(Timestamp.valueOf("2022-04-23 00:00:00").getTime()),
            new Timestamp(Timestamp.valueOf("2022-04-25 00:00:00").getTime()),
            new Rooms(),
            new User());
    HttpStatus expectedCode = HttpStatus.OK;
    // Act
    ResponseEntity<?> reservations = reservationsService.getReservationsById(1L);
    HttpStatus code = reservations.getStatusCode();
    // Assert
    Assertions.assertThat(((ReservationResponse) reservations.getBody()).getStartDate())
        .isEqualTo(expectedReservation.getStartDate());
    Assertions.assertThat(code).isEqualTo(expectedCode);
  }

  @Test
  @Description("User tries to save Reservation")
  public void saveReservationShouldReturnReservation() {
    // Arrange
    Reservations expectedReservation =
        new Reservations(
            1L,
            new Timestamp(Timestamp.valueOf("2023-04-23 00:00:00").getTime()),
            new Timestamp(Timestamp.valueOf("2023-04-25 00:00:00").getTime()),
            new Rooms(),
            new User());
    HttpStatus expectedCode = HttpStatus.CREATED;
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken("1", null);
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    // Act
    ResponseEntity<?> reservations =
        reservationsService.saveReservation(
            ReservationRequest.builder()
                .startDate(new Timestamp(Timestamp.valueOf("2023-04-23 00:00:00").getTime()))
                .endDate(new Timestamp(Timestamp.valueOf("2023-04-25 00:00:00").getTime()))
                .roomId(1L)
                .build());
    HttpStatus code = reservations.getStatusCode();
    // Assert
    Assertions.assertThat(((ReservationResponse) reservations.getBody()).getStartDate())
        .isEqualTo(expectedReservation.getStartDate());
    Assertions.assertThat(code).isEqualTo(expectedCode);
  }

  @Test
  @Description("User tries to update Reservation")
  public void updateReservationShouldReturnReservation() {
    // Arrange
    ReservationRequest reservationRequest =  ReservationRequest.builder()
            .startDate(new Timestamp(Timestamp.valueOf("2023-04-23 00:00:00").getTime()))
            .endDate(new Timestamp(Timestamp.valueOf("2023-04-25 00:00:00").getTime()))
            .roomId(1L)
            .build();

    HttpStatus expectedCode = HttpStatus.OK;
    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken("3", null);
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    // Act
    ResponseEntity<?> reservations =
            reservationsService.updateReservation(1L, reservationRequest);
    HttpStatus code = reservations.getStatusCode();
    // Assert
    Assertions.assertThat(((ReservationResponse) reservations.getBody()).getStartDate())
            .isEqualTo(reservationRequest.getStartDate());
    Assertions.assertThat(code).isEqualTo(expectedCode);
  }

  @Test
  @Description("User tries to delete Reservation")
  public void deleteReservationShouldReturnReservation() {
    // Arrange
    HttpStatus expectedCode = HttpStatus.OK;
    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken("3", null);
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    // Act
    ResponseEntity<?> reservations =
            reservationsService.deleteReservation(1L);
    HttpStatus code = reservations.getStatusCode();
    // Assert
    Assertions.assertThat(code).isEqualTo(expectedCode);
  }
}
