package com.example.refugeeshelter.service;

import com.example.refugeeshelter.dto.response.RoomsResponse;
import com.example.refugeeshelter.dto.response.UserResponse;
import com.example.refugeeshelter.entities.Rooms;
import com.example.refugeeshelter.entities.User;
import com.example.refugeeshelter.exceptions.FileStorageException;
import com.example.refugeeshelter.exceptions.ResourceNotFoundException;
import com.example.refugeeshelter.repositories.RoomsRepo;
import com.example.refugeeshelter.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomsService {
  private final RoomsRepo roomsRepo;
  private final UserRepo userRepo;

  public List<RoomsResponse> getRooms() {
    List<Rooms> roomsList = roomsRepo.findAll();
    List<RoomsResponse> roomsResponse = new ArrayList<>(roomsList.size());

    for (Rooms room : roomsList) {
      UserResponse userResponse =
          new UserResponse(
              room.getUser().getId(),
              room.getUser().getName(),
              room.getUser().getEmail(),
              room.getUser().getPhone());

      roomsResponse.add(
          new RoomsResponse(
              room.getId(),
              room.getAddress(),
              room.getLatitude(),
              room.getLongitude(),
              room.getPrice(),
              room.getImageUrl(),
              room.getHasKitchen(),
              room.getHasBathroom(),
              room.getDescription(),
              userResponse));
    }
    return roomsResponse;
  }

  public ResponseEntity<?> getRoomById(Long id) {
    Rooms rooms =
        roomsRepo
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cannot find room with", "id", id));
    UserResponse userResponse =
        new UserResponse(
            rooms.getUser().getId(),
            rooms.getUser().getName(),
            rooms.getUser().getEmail(),
            rooms.getUser().getPhone());

    return ResponseEntity.ok()
        .body(
            new RoomsResponse(
                rooms.getId(),
                rooms.getAddress(),
                rooms.getLatitude(),
                rooms.getLongitude(),
                rooms.getPrice(),
                rooms.getImageUrl(),
                rooms.getHasKitchen(),
                rooms.getHasBathroom(),
                rooms.getDescription(),
                userResponse));
  }

  public List<Rooms> getRoomsByOwnerId(Long ownerId) {
    return roomsRepo
        .findByUserId(ownerId)
        .orElseThrow(() -> new FileStorageException("Cannot find room with owner id = " + ownerId));
  }

  public Rooms saveRoom(Long ownerId, Rooms rooms) {
    User user =
        userRepo
            .findById(ownerId)
            .orElseThrow(() -> new FileStorageException("Cannot find owner with id = " + ownerId));
    rooms.setUser(user);
    return roomsRepo.save(rooms);
  }

  public Rooms updateRoom(Long id, Rooms newRoom) {
    Rooms room =
        roomsRepo
            .findById(id)
            .orElseThrow(() -> new FileStorageException("Cannot find room with id = " + id));
    room.setFields(newRoom);
    return roomsRepo.save(room);
  }

  public Rooms patchRoom(Long id, Rooms newRoom) {
    Rooms room =
        roomsRepo
            .findById(id)
            .orElseThrow(() -> new FileStorageException("Cannot find room with id = " + id));

    if (newRoom.getHasBathroom() != null) {
      room.setHasBathroom(newRoom.getHasBathroom());
    }

    if (newRoom.getHasKitchen() != null) {
      room.setHasKitchen(newRoom.getHasKitchen());
    }

    if (newRoom.getAddress() != null) {
      room.setAddress(newRoom.getAddress());
    }

    if (newRoom.getDescription() != null) {
      room.setDescription(newRoom.getDescription());
    }

    if (newRoom.getLatitude() != null) {
      room.setLatitude(newRoom.getLatitude());
    }

    if (newRoom.getLongitude() != null) {
      room.setLongitude(newRoom.getLongitude());
    }

    if (newRoom.getPrice() != null) {
      room.setPrice(newRoom.getPrice());
    }

    if (newRoom.getUser() != null) {
      room.setUser(newRoom.getUser());
    }
    return roomsRepo.save(room);
  }

  public Rooms deleteRoom(Long id) {
    Rooms room =
        roomsRepo
            .findById(id)
            .orElseThrow(() -> new FileStorageException("Cannot find room with id = " + id));
    roomsRepo.deleteById(id);
    return room;
  }
}
