package com.example.refugeeshelter.controllers;

import com.example.refugeeshelter.dto.response.RoomsDTO;
import com.example.refugeeshelter.service.RoomsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class RoomsController {
  private final RoomsService roomsService;

  @GetMapping(value = "/rooms")
  public ResponseEntity<?> getRooms() {
    return roomsService.getRooms();
  }

  @GetMapping("/users/{ownerId}/rooms")
  public ResponseEntity<?> getUserRooms(@PathVariable Long ownerId) {
    return roomsService.getRoomsByOwnerId(ownerId);
  }


  @GetMapping("/rooms/{id}")
  public ResponseEntity<?> getRoomById(@PathVariable Long id) {
    return roomsService.getRoomById(id);
  }

  @PostMapping(value = "/rooms", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> saveRoom(@RequestBody RoomsDTO newRoom) {
    return roomsService.saveRoom(newRoom);
  }

  @PutMapping("/rooms/{id}")
  public ResponseEntity<?> updateRoomById(
      @PathVariable Long id, @RequestBody RoomsDTO newRoom) {
    return roomsService.updateRoom(id, newRoom);
  }

  @PatchMapping("/rooms/{id}")
  public ResponseEntity<?> patchRoomById(
      @PathVariable Long id, @RequestBody RoomsDTO newRoom) {
    return roomsService.patchRoom(id, newRoom);
  }

  @DeleteMapping("/rooms/{id}")
  public ResponseEntity<?> deleteRoomById(@PathVariable Long id) {
    return roomsService.deleteRoom(id);
  }
}
