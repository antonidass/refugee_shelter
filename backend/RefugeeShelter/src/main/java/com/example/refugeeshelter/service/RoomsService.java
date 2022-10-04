package com.example.refugeeshelter.service;

import com.example.refugeeshelter.entities.Rooms;
import com.example.refugeeshelter.entities.User;
import com.example.refugeeshelter.exceptions.FileStorageException;
import com.example.refugeeshelter.repositories.RoomsRepo;
import com.example.refugeeshelter.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomsService {
    private final RoomsRepo roomsRepo;
    private final UserRepo userRepo;

    public List<Rooms> getRooms() {
        return roomsRepo.findAll();
    }

    public Rooms getRoomById(Long id) {
        return roomsRepo.findById(id).orElseThrow(() -> new FileStorageException("Cannot find room with id = " + id));
    }

    public List<Rooms> getRoomsByOwnerId(Long ownerId) {
        return roomsRepo.findByUserId(ownerId).orElseThrow(() -> new FileStorageException("Cannot find room with owner id = " + ownerId));
    }

    public Rooms saveRoom(Long ownerId, Rooms rooms) {
        User user = userRepo.findById(ownerId).orElseThrow(() -> new FileStorageException("Cannot find owner with id = " + ownerId));

        rooms.setUser(user);
        return roomsRepo.save(rooms);
    }

    public Rooms updateRoom(Long id, Rooms newRoom)  {
        Rooms room = roomsRepo.findById(id).orElseThrow(() -> new FileStorageException("Cannot find room with id = " + id));
        room.setFields(newRoom);
        return roomsRepo.save(room);
    }

    public Rooms patchRoom(Long id, Rooms newRoom)  {
        Rooms room = roomsRepo.findById(id).orElseThrow(() -> new FileStorageException("Cannot find room with id = " + id));

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

    public Rooms deleteRoom(Long id)  {
        Rooms room = roomsRepo.findById(id).orElseThrow(() -> new FileStorageException("Cannot find room with id = " + id));
        roomsRepo.deleteById(id);
        return room;
    }
}
