package com.example.refugeeshelter.dto;

import com.example.refugeeshelter.dto.response.ReservationResponse;
import com.example.refugeeshelter.dto.response.RoomsResponse;
import com.example.refugeeshelter.dto.response.UserResponse;
import com.example.refugeeshelter.entities.Reservations;
import com.example.refugeeshelter.filter.FilterRoomsObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ReservationMapper {

  private final RoomMapper roomMapper;

  public ReservationMapper(RoomMapper roomMapper) {
    this.roomMapper = roomMapper;
  }

  public ReservationResponse toDto(Reservations reservations) {
    RoomsResponse roomsResponse = roomMapper.toDto(reservations.getRoom());

    UserResponse userResponse =
        new UserResponse(
            reservations.getUser().getId(),
            reservations.getUser().getName(),
            reservations.getUser().getEmail(),
            reservations.getUser().getPhone());

    return new ReservationResponse(
        reservations.getId(),
        reservations.getStartDate(),
        reservations.getEndDate(),
        roomsResponse,
        userResponse);
  }

  // TODO fix fix fix
  public ReservationResponse toDto(Reservations reservations, Boolean deleteUserField) {
    RoomsResponse roomsResponse = roomMapper.toDto(reservations.getRoom());

    UserResponse userResponse =
            new UserResponse(
                    reservations.getUser().getId(),
                    reservations.getUser().getName(),
                    reservations.getUser().getEmail(),
                    reservations.getUser().getPhone());

    // TODO fix this with another DTO ?
    roomsResponse.setUser(null);
    return new ReservationResponse(
            reservations.getId(),
            reservations.getStartDate(),
            reservations.getEndDate(),
            roomsResponse,
            userResponse);
  }

  public Reservations toReservation(ReservationResponse reservationResponse) {
    return new Reservations(
        reservationResponse.getId(),
        reservationResponse.getStartDate(),
        reservationResponse.getEndDate());
  }
}
