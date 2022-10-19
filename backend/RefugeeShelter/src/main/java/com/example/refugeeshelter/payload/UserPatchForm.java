package com.example.refugeeshelter.payload;

import lombok.Data;


@Data
public class UserPatchForm {
    private String name;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String role;
}

