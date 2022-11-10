package com.example.refugeeshelter.service;

import com.example.refugeeshelter.dto.RoomMapper;
import com.example.refugeeshelter.dto.response.RoomsDTO;
import com.example.refugeeshelter.entities.User;
import org.assertj.core.api.Assertions;
import com.example.refugeeshelter.entities.Rooms;
import com.example.refugeeshelter.repositories.ReservationsRepo;
import com.example.refugeeshelter.repositories.RoomsRepo;
import com.example.refugeeshelter.repositories.UserRepo;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;

@Epic("Service Test")
@Feature("Room Service Test")
@Slf4j
@ExtendWith(MockitoExtension.class)
class RoomsServiceTest {
  @Mock private UserRepo userRepo;

  @Mock private RoomsRepo roomsRepo;

  @Mock private ReservationsRepo reservationsRepo;

  @Mock private RoomMapper roomMapper;

  @InjectMocks private RoomsService roomsService;

  @Test
  @Description("User tries to get Rooms")
  void getRooms() {
    final Rooms room1 = mock(Rooms.class);
    final Rooms room2 = mock(Rooms.class);
    List<Rooms> rooms = Arrays.asList(room1, room2);
    when(roomsRepo.findAll()).thenReturn(rooms);
    List<RoomsDTO> roomsResponse = new ArrayList<>(rooms.size());
    rooms.forEach(room -> roomsResponse.add(roomMapper.toDto(room)));

    final ResponseEntity<?> roomsRes = roomsService.getRooms();

    Assertions.assertThat(roomsRes.getBody()).isEqualTo(roomsResponse);
    verify(roomsRepo).findAll();
  }

  @Test
  @Description("User tries to get Room By Id")
  void getRoomById() {
    final Rooms room = mock(Rooms.class);
    Long id = 1L;
    when(roomsRepo.findById(id)).thenReturn(java.util.Optional.ofNullable(room));

    final ResponseEntity<?> roomRes = roomsService.getRoomById(id);

    Assertions.assertThat(roomRes.getBody())
        .isEqualTo(ResponseEntity.ok().body(roomMapper.toDto(room)).getBody());
    verify(roomsRepo).findById(id);
  }

  @Test
  @Description("User tries to get Room By Owner Id")
  void getRoomsByOwnerId() {
    Long ownerId = 1L;
    final Rooms room = mock(Rooms.class);
    final User user = mock(User.class);
    when(userRepo.findById(ownerId)).thenReturn(java.util.Optional.ofNullable(user));
    when(roomsRepo.findByUserId(ownerId)).thenReturn(java.util.Optional.of(Arrays.asList(room)));

    final ResponseEntity<?> roomRes = roomsService.getRoomsByOwnerId(ownerId);

    Assertions.assertThat(((MappingJacksonValue) roomRes.getBody()).getValue())
        .isEqualTo(Arrays.asList(roomMapper.toDto(room)));
    verify(userRepo).findById(ownerId);
    verify(roomsRepo).findByUserId(ownerId);
  }

  @Test
  @Description("User tries to save Room")
  void saveRoom() {
    Long ownerId = 1L;
    Rooms room = Rooms.builder().latitude(123.1).longitude(321.1).price(12300L).build();
    RoomsDTO roomsDTO = RoomsDTO.builder().latitude(123.1).longitude(321.1).price(12300L).build();
    User user = User.builder().username("testusername").password("test").name("testname").build();
    final Authentication authentication = mock(Authentication.class);
    final SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getName()).thenReturn(String.valueOf(ownerId));
    SecurityContextHolder.setContext(securityContext);
    when(userRepo.findById(ownerId)).thenReturn(java.util.Optional.ofNullable(user));
    when(roomMapper.toRooms(roomsDTO)).thenReturn(room);
    when(roomsRepo.save(room)).thenReturn(room);
    when(roomMapper.toDto(room)).thenReturn(roomsDTO);

    final ResponseEntity<?> roomRes = roomsService.saveRoom(roomMapper.toDto(room));

    Assertions.assertThat(((RoomsDTO) roomRes.getBody()).getLatitude()).isEqualTo(123.1);
    verify(userRepo).findById(ownerId);
    verify(roomsRepo).save(room);
  }

  @Test
  @Description("User tries to update Room")
  void updateRoom() {
    Long ownerId = 1L;
    Long roomId = 2L;
    User user =
        User.builder()
            .username("testusername")
            .password("test")
            .name("testname")
            .id(ownerId)
            .build();
    Rooms room = Rooms.builder().latitude(123.1).longitude(321.1).price(12300L).user(user).build();
    Rooms newRoom = Rooms.builder().latitude(1.1).longitude(1.1).price(1L).user(user).build();
    RoomsDTO newRoomsDTO = RoomsDTO.builder().latitude(1.1).longitude(1.1).price(1L).build();
    final Authentication authentication = mock(Authentication.class);
    final SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getName()).thenReturn(String.valueOf(ownerId));
    SecurityContextHolder.setContext(securityContext);
    when(roomsRepo.findById(roomId)).thenReturn(java.util.Optional.ofNullable(room));
    when(roomMapper.toRooms(newRoomsDTO)).thenReturn(newRoom);
    when(roomsRepo.save(newRoom)).thenReturn(newRoom);
    when(roomMapper.toDto(newRoom)).thenReturn(newRoomsDTO);

    final ResponseEntity<?> roomRes = roomsService.updateRoom(roomId, newRoomsDTO);

    Assertions.assertThat(((RoomsDTO) roomRes.getBody()).getLatitude()).isEqualTo(1.1);
    verify(roomsRepo).findById(roomId);
    verify(roomsRepo).save(newRoom);
  }

  @Test
  @Description("User tries to patch Room")
  void patchRoom() {
    Long ownerId = 1L;
    Long roomId = 2L;
    User user =
        User.builder()
            .username("testusername")
            .password("test")
            .name("testname")
            .id(ownerId)
            .build();
    Rooms room = Rooms.builder().latitude(123.1).longitude(321.1).price(12300L).user(user).build();
    RoomsDTO newRoomsDTO = RoomsDTO.builder().price(1L).build();
    final Authentication authentication = mock(Authentication.class);
    final SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getName()).thenReturn(String.valueOf(ownerId));
    SecurityContextHolder.setContext(securityContext);
    when(roomsRepo.findById(roomId)).thenReturn(java.util.Optional.ofNullable(room));
    when(roomsRepo.save(room)).thenReturn(room);
    when(roomMapper.toDto(room)).thenReturn(newRoomsDTO);

    final ResponseEntity<?> roomRes = roomsService.patchRoom(roomId, newRoomsDTO);

    Assertions.assertThat(((RoomsDTO) roomRes.getBody()).getPrice())
        .isEqualTo(newRoomsDTO.getPrice());
    verify(roomsRepo).findById(roomId);
    verify(roomsRepo).save(room);
  }

  @Test
  @Description("User tries to delete Room")
  void deleteRoom() {
    Long ownerId = 1L;
    Long roomId = 2L;
    User user =
        User.builder()
            .username("testusername")
            .password("test")
            .name("testname")
            .id(ownerId)
            .build();
    Rooms room = Rooms.builder().latitude(123.1).longitude(321.1).price(12300L).user(user).build();
    final Authentication authentication = mock(Authentication.class);
    final SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getName()).thenReturn(String.valueOf(ownerId));
    SecurityContextHolder.setContext(securityContext);
    when(roomsRepo.findById(roomId)).thenReturn(java.util.Optional.ofNullable(room));
    when(reservationsRepo.findByRoomId(roomId))
        .thenReturn(java.util.Optional.of(new ArrayList<>()));

    final ResponseEntity<?> roomRes = roomsService.deleteRoom(roomId);

    Assertions.assertThat(roomRes.getStatusCode()).isEqualTo(HttpStatus.OK);
    verify(roomsRepo).findById(roomId);
    verify(roomsRepo).delete(room);
  }
}
