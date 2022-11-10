package com.example.refugeeshelter.dto;

import com.example.refugeeshelter.dto.response.RoomsDTO;
import com.example.refugeeshelter.dto.response.UserDTO;
import com.example.refugeeshelter.entities.Rooms;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
  public RoomsDTO toDto(Rooms room) {
    UserDTO userResponse = new UserDTO(
            room.getUser().getId(),
            room.getUser().getName(),
            room.getUser().getEmail(),
            room.getUser().getPhone());

    return new RoomsDTO(
        room.getId(),
        room.getAddress(),
        room.getLatitude(),
        room.getLongitude(),
        room.getPrice(),
        room.getImageUrl(),
        room.getHasKitchen(),
        room.getHasBathroom(),
        room.getDescription(),
        userResponse);
  }

  public Rooms toRooms(RoomsDTO roomsResponse) {
    return new Rooms(
        roomsResponse.getId(),
        roomsResponse.getAddress(),
        roomsResponse.getLatitude(),
        roomsResponse.getLongitude(),
        roomsResponse.getPrice(),
        roomsResponse.getImageUrl(),
        roomsResponse.getHasKitchen(),
        roomsResponse.getHasBathroom(),
        roomsResponse.getDescription());
  }
}
