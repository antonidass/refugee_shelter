package com.example.refugeeshelter.controllers;


import com.example.refugeeshelter.entities.Rooms;
import com.example.refugeeshelter.exceptions.FileStorageException;
import com.example.refugeeshelter.filter.FilteredReservationsObjectMapper;
import com.example.refugeeshelter.service.RoomsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class RoomsController {
    private final RoomsService roomsService;

    @GetMapping("/rooms")
    public ResponseEntity<?> getRooms() {
        List<Rooms> roomsList = roomsService.getRooms();
        return ResponseEntity.ok().body(roomsList);
    }

    @GetMapping("/rooms/user/{ownerId}")
    public ResponseEntity<?> getUserRooms(@PathVariable Long ownerId) {
        List<Rooms> rooms;
        try {
            rooms = roomsService.getRoomsByOwnerId(ownerId);
        } catch (FileStorageException e) {
            return ResponseEntity.badRequest().body("Room with owner id = " + ownerId + " not founded!");
        }

        MappingJacksonValue filteredRooms = FilteredReservationsObjectMapper.getInstance().getMappingJacksonValue(rooms);
        return ResponseEntity.ok().body(filteredRooms);
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable Long id) {
        Rooms room;
        try {
            room = roomsService.getRoomById(id);
        } catch (FileStorageException e) {
            return ResponseEntity.badRequest().body("Room with id = " + id + " not founded!");
        }
        return ResponseEntity.ok().body(room);
    }

    @PostMapping("/rooms/{ownerId}")
    public ResponseEntity<?> saveRoom(@PathVariable Long ownerId, @RequestBody Rooms newRoom) {
        Rooms room;
        try {
            room = roomsService.saveRoom(ownerId, newRoom);
        } catch (FileStorageException e) {
            return ResponseEntity.badRequest().body("Owner with id = " + ownerId + " not founded!");
        }
        return ResponseEntity.ok().body(room);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<?> updateRoomById(@PathVariable Long id, @RequestBody Rooms newRoom) {
        Rooms room;
        try {
            room = roomsService.updateRoom(id, newRoom);
        } catch (FileStorageException e) {
            return ResponseEntity.badRequest().body("Room with id = " + id + " not founded!");
        }
        return ResponseEntity.ok().body(room);
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<?> deleteRoomById(@PathVariable Long id) {
        Rooms room;
        try {
            room = roomsService.deleteRoom(id);
        } catch (FileStorageException e) {
            return ResponseEntity.badRequest().body("Room with id = " + id + " not founded!");
        }
        return ResponseEntity.ok().body(room);
    }
}
