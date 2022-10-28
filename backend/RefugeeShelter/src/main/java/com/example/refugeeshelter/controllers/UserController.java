package com.example.refugeeshelter.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.refugeeshelter.dto.ApiResponse;
import com.example.refugeeshelter.entities.Role;
import com.example.refugeeshelter.entities.User;
import com.example.refugeeshelter.exceptions.FileStorageException;
import com.example.refugeeshelter.payload.UserPatchForm;
import com.example.refugeeshelter.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class UserController {
  private final UserServiceImpl userService;

  @GetMapping("/users")
  public ResponseEntity<?> getUsers() {
    return userService.getUsers();
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<?> getUsers(@PathVariable Long id) {
    return userService.getUserById(id);
  }

  @PostMapping("/users")
  public ResponseEntity<?> saveUser(@RequestBody User user) {
    return userService.saveUser(user);
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody User user) {
    return userService.registerUser(user);
  }

  @PutMapping("/users/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User newUser) {
    return userService.updateUser(id, newUser);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    return userService.deleteUser(id);
  }

  @PostMapping("/roles")
  public ResponseEntity<Role> saveRole(@RequestBody Role role) {
    URI uri =
        URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/roles/save")
                .toUriString());
    return ResponseEntity.created(uri).body(userService.saveRole(role));
  }

  @PatchMapping("/users/{id}")
  public ResponseEntity<?> addRoleToUser(
      @PathVariable Long id, @RequestBody UserPatchForm newUser) {
    return ResponseEntity.ok().body(new ApiResponse(true, "Success!"));
  }

  @GetMapping("/token/refresh")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String authorizationHeader = request.getHeader(AUTHORIZATION);

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      try {
        String refreshToken = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);

        String username = decodedJWT.getSubject();
        User user = userService.getUser(username);

        String accessToken =
            JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURI())
                .withClaim("userId", user.getId())
                .withClaim(
                    "roles",
                    user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(algorithm);

        Map<String, String> token = new HashMap<>();
        token.put("access_token", accessToken);
        token.put("refresh_token", refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), token);
      } catch (Exception e) {
        response.setStatus(FORBIDDEN.value());
        Map<String, String> error = new HashMap<>();
        error.put("error_message", e.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
      }
    } else {
      throw new RuntimeException("Refresh token is missing");
    }
  }
}
