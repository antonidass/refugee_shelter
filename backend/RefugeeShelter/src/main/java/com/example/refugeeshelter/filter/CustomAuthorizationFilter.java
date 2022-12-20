package com.example.refugeeshelter.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // TODO Норм ?
    log.info("servlet path = {} method = {} uri = {}", request.getServletPath(), request.getMethod(), request.getRequestURI());
    String origin = request.getHeader("origin");
    log.info("Origin = ", origin);
    response.addHeader("Access-Control-Allow-Origin", origin);
//    response.addHeader("Access-Control-Allow-Origin", "/**");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.addHeader("Access-Control-Allow-Credentials", "true");
    response.addHeader("Access-Control-Allow-Headers",
            request.getHeader("Access-Control-Request-Headers"));

    if (request.getServletPath().equals("/favicon.ico")) {
      return;
    }

    if (request.getServletPath().equals("/api/v1/login")
        || request.getServletPath().equals("/api/v1/token/refresh")
        || request.getServletPath().equals("/api/v1/")
        || request.getServletPath().equals("/api/v1/register")
            || request.getServletPath().contains("/api/v1/image")
        || (request.getServletPath().contains("/api/v1/reservations")
            && request.getMethod().equals("GET"))
        || (request.getServletPath().contains("/api/v1/rooms")
            && request.getMethod().equals("GET"))) {
      log.info("Handle without parsing token...");
      filterChain.doFilter(request, response);
    } else {
      String authorizationHeader = request.getHeader(AUTHORIZATION);
      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        try {
          String token = authorizationHeader.substring("Bearer ".length());
          Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
          JWTVerifier verifier = JWT.require(algorithm).build();
          DecodedJWT decodedJWT = verifier.verify(token);
          String username = decodedJWT.getSubject();
          String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
          Long userId = decodedJWT.getClaim("userId").asLong();
          log.info("User id after authorization :{}", userId);
          Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
          stream(roles)
              .forEach(
                  role -> {
                    authorities.add(new SimpleGrantedAuthority(role));
                  });

          UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(userId, null, authorities);
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
          filterChain.doFilter(request, response);

        } catch (Exception e) {
          // Если попытка авторизации пользователя не удалась (неверный токен)
          log.error("Error logging in: {}", e.getMessage());
          response.setStatus(NOT_FOUND.value());
          Map<String, String> error = new HashMap<>();
          error.put(
              "error_message", "Error while authorization... Please check login and password");
          response.setContentType(APPLICATION_JSON_VALUE);
          new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
      } else {
        // Если пользователь пытается получить доступ к закрытому апи то кидаем 401
        log.info("Access error 401");
        response.setStatus(UNAUTHORIZED.value());
        Map<String, String> error = new HashMap<>();
        error.put("error_message", "You should login for this method");
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
      }
    }
  }
}
