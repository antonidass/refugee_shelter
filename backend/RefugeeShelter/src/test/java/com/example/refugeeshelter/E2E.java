package com.example.refugeeshelter;

import com.example.refugeeshelter.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.SneakyThrows;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Iterator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Epic("E2E Test")
@Feature("User Tries Make Reservation Test")
@ActiveProfiles("test")
@AutoConfigureMockMvc()
@SpringBootTest
public class E2E {
  private String sqlUrl = "jdbc:postgresql://localhost:5433/ref_shel";
  @Autowired private MockMvc mvc;
  @Autowired private ObjectMapper objectMapper;

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

  @SneakyThrows
  @Test
  @Description("Try To Login and Make reservation")
  public void e2eTest() {
    // Arrange
    Gson gson = new Gson();
    User user = User.builder().password("test").username("test").build();
    String body = gson.toJson(user, user.getClass());

    // Act
    mvc.perform(
            post("/api/v1/register")
                .servletPath("/api/v1/register")
                .content(body)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    HashMap<String, String> tokenMap = new HashMap<String, String>();


    mvc.perform(
            post("/api/v1/login")
                .servletPath("/api/v1/login")
                .param("username", "test")
                .param("password", "test")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            mvcResult -> {
              String json = mvcResult.getResponse().getContentAsString();
              JSONObject jObject = new JSONObject(json);
              Iterator<?> keys = jObject.keys();

              while( keys.hasNext() ){
                String key = (String)keys.next();
                String value = jObject.getString(key);
                tokenMap.put(key, value);
              }
            });

    mvc.perform(
            get("/api/v1/rooms/1")
                    .header("Authorization", "Bearer " + tokenMap.get("access_token"))
                    .servletPath("/api/v1/rooms/1")
    ).andExpect(status().isOk());

    HashMap<String, Long> json = new HashMap<>();
    json.put("startDate", 2000032L);
    json.put("endDate", 30000032L);
    json.put("roomId", 1L);
    String jsonStr = gson.toJson(json, json.getClass());
    mvc.perform(
            post("/api/v1/reservations")
                    .content(jsonStr)
                    .servletPath("/api/v1/reservations")
                    .header("Authorization", "Bearer " + tokenMap.get("access_token"))
                    .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(status().isCreated());
  }
}
