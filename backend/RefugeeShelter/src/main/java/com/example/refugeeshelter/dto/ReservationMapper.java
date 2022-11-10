package com.example.refugeeshelter.dto;

import com.example.refugeeshelter.dto.response.ReservationResponse;
import com.example.refugeeshelter.dto.response.RoomsDTO;
import com.example.refugeeshelter.dto.response.UserDTO;
import com.example.refugeeshelter.entities.Reservations;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

  private final RoomMapper roomMapper;

  public ReservationMapper(RoomMapper roomMapper) {
    this.roomMapper = roomMapper;
  }

  public ReservationResponse toDto(Reservations reservations) {
    RoomsDTO roomsResponse = roomMapper.toDto(reservations.getRoom());

    UserDTO userResponse =
        new UserDTO(
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
    RoomsDTO roomsResponse = roomMapper.toDto(reservations.getRoom());

    UserDTO userResponse =
            new UserDTO(
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
