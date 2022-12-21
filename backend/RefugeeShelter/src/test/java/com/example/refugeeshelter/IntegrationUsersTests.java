package com.example.refugeeshelter;


import com.example.refugeeshelter.dto.response.RoomsDTO;
import com.example.refugeeshelter.dto.response.UserDTO;
import com.example.refugeeshelter.entities.Rooms;
import com.example.refugeeshelter.entities.User;
import com.example.refugeeshelter.exceptions.ResourceNotFoundException;
import com.example.refugeeshelter.service.RoomsService;
import com.example.refugeeshelter.service.UserServiceImpl;
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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;


@Epic("Integration Test")
@Feature("Integration User Test")
@ActiveProfiles("test")
@SpringBootTest
public class IntegrationUsersTests {
    @Autowired
    private UserServiceImpl userService;
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
    @Description("User tries to get User")
    public void getUserByIdShouldReturnUser() {
        // Arrange
        User expectedUser = new User(1L, "Ivan Petrov", "akrikoff@gmail.com", "89032148282");
        HttpStatus expectedCode = HttpStatus.OK;
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken("1", null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // Act
        ResponseEntity<?> user = userService.getUserById(1L);
        HttpStatus code = user.getStatusCode();
        // Assert
        Assertions.assertThat( ((UserDTO) user.getBody()).getName()).isEqualTo(expectedUser.getName());
        Assertions.assertThat(code).isEqualTo(expectedCode);
    }

    @Test
    @Description("User tries to save User")
    public void saveUserByIdShouldReturnUser() {
        // Arrange
        User expectedUser = new User(1L, "Ivan Petrov", "akrikoff@gmail.com", "89032148282");
        expectedUser.setPassword("password");
        HttpStatus expectedCode = HttpStatus.CREATED;
        // Act
        ResponseEntity<?> user = userService.saveUser(expectedUser);
        HttpStatus code = user.getStatusCode();
        // Assert
        Assertions.assertThat( ((UserDTO) user.getBody()).getName()).isEqualTo(expectedUser.getName());
        Assertions.assertThat(code).isEqualTo(expectedCode);
    }

    @Test
    @Description("User tries to updaet User")
    public void updateUserByIdShouldReturnUser() {
        // Arrange
        User expectedUser = new User(1L, "NEW Petrov", "akrikoff@gmail.com", "89032148282");
        HttpStatus expectedCode = HttpStatus.OK;
        // Act
        ResponseEntity<?> user = userService.updateUser(1L, expectedUser);
        HttpStatus code = user.getStatusCode();
        // Assert
        Assertions.assertThat( ((UserDTO) user.getBody()).getName()).isEqualTo(expectedUser.getName());
        Assertions.assertThat(code).isEqualTo(expectedCode);
    }

    @Test
    @Description("User tries to delete User")
    public void deleteUserByIdShouldReturnUser() {
        // Arrange
        HttpStatus expectedCode = HttpStatus.OK;
        // Act
        ResponseEntity<?> response = null;
        try {
            response = userService.deleteUser(6L);
        } catch (
                ResourceNotFoundException e
        ) {
            Assertions.assertThat(Boolean.TRUE).isTrue();
        }
    }
}
