package com.example.refugeeshelter;

import com.example.refugeeshelter.dto.response.RoomsDTO;
import com.example.refugeeshelter.entities.Rooms;
import com.example.refugeeshelter.service.RoomsService;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;


@Epic("Integration Test")
@Feature("Integration Room Test")
@ActiveProfiles("test")
@SpringBootTest
class IntegrationRoomsTests {
  @Autowired private RoomsService roomsService;

  @Value("${spring.datasource.url}")
  private String sqlUrl;

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
  @Description("User tries to get Room")
  public void getRoomByIdShouldReturnRoom() {
    // Arrange
    Rooms expectedRoom = new Rooms(1L,"Moscow, Borovay St. 8, 123", 50.1, 0.0, 1200L, "1", 4L, 2L, "This is good rooms for refugees....");
    HttpStatus expectedCode = HttpStatus.OK;
    // Act
    ResponseEntity<?> room = roomsService.getRoomById(1L);
    HttpStatus code = room.getStatusCode();
    // Assert
    Assertions.assertThat( ((RoomsDTO) room.getBody()).getPrice()).isEqualTo(expectedRoom.getPrice());
    Assertions.assertThat(code).isEqualTo(expectedCode);
  }

  @Test
  @Description("User tries to save Room")
  public void saveRoomShouldReturnRoom() {
    // Arrange
    RoomsDTO roomForSave = RoomsDTO.builder().price(100L).longitude(10.1).latitude(20.20).address("Moscow, Lenina 15").build();
    HttpStatus expectedCode = HttpStatus.CREATED;
    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken("1", null);
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    // Act
    ResponseEntity<?> room = roomsService.saveRoom(roomForSave);
    HttpStatus code = room.getStatusCode();
    // Assert
    Assertions.assertThat( ((RoomsDTO) room.getBody()).getPrice()).isEqualTo(roomForSave.getPrice());
    Assertions.assertThat(code).isEqualTo(expectedCode);
  }

  @Test
  @Description("User tries to update Room")
  public void updateRoomShouldReturnRoom() {
    // Arrange
    RoomsDTO roomForSave = RoomsDTO.builder().price(100L).longitude(10.1).latitude(20.20).address("Moscow, Lenina 15").build();
    HttpStatus expectedCode = HttpStatus.OK;
    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken("1", null);
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    // Act
    ResponseEntity<?> room = roomsService.updateRoom(1L, roomForSave);
    HttpStatus code = room.getStatusCode();
    // Assert
    Assertions.assertThat( ((RoomsDTO) room.getBody()).getPrice()).isEqualTo(roomForSave.getPrice());
    Assertions.assertThat(code).isEqualTo(expectedCode);
  }

  @Test
  @Description("User tries to delete Room")
  public void deleteRoomShouldReturnRoom() {
    // Arrange
    HttpStatus expectedCode = HttpStatus.OK;
    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken("2", null);
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    // Act
    ResponseEntity<?> room = roomsService.deleteRoom(2L);
    HttpStatus code = room.getStatusCode();
    // Assert
    Assertions.assertThat(code).isEqualTo(expectedCode);
  }
}
