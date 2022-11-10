package com.example.refugeeshelter.dto;

import com.example.refugeeshelter.dto.response.UserDTO;
import com.example.refugeeshelter.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone());
    }

    // todo kek
    public User toUser(UserDTO userResponse) {
        return new User(
                userResponse.getId(),
                userResponse.getName(),
                userResponse.getEmail(),
                userResponse.getPhone());
    }
}
