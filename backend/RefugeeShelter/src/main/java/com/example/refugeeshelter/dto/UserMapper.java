package com.example.refugeeshelter.dto;

import com.example.refugeeshelter.dto.response.RoomsResponse;
import com.example.refugeeshelter.dto.response.UserResponse;
import com.example.refugeeshelter.entities.Rooms;
import com.example.refugeeshelter.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone());
    }

    // todo kek
    public User toUser(UserResponse userResponse) {
        return new User(
                userResponse.getId(),
                userResponse.getName(),
                userResponse.getEmail(),
                userResponse.getPhone());
    }
}
