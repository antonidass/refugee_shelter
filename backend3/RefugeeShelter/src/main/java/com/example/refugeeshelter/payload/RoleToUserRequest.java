package com.example.refugeeshelter.payload;

import lombok.Data;

@Data
public class RoleToUserRequest {
    private String userName;
    private String roleName;
}

