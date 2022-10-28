package com.example.refugeeshelter.dto;

import com.example.refugeeshelter.dto.response.RoomsResponse;
import com.example.refugeeshelter.dto.response.UserResponse;
import com.example.refugeeshelter.entities.Rooms;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
  public RoomsResponse toDto(Rooms room) {
    UserResponse userResponse = new UserResponse(
            room.getUser().getId(),
            room.getUser().getName(),
            room.getUser().getEmail(),
            room.getUser().getPhone());

    return new RoomsResponse(
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

  public Rooms toRooms(RoomsResponse roomsResponse) {
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
