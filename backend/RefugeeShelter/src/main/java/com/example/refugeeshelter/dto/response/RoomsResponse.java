package com.example.refugeeshelter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RoomsResponse {
    private Long id;
    private String address;
    private Double latitude;
    private Double longitude;
    private Long price;
    private String imageUrl;
    private Boolean hasKitchen;
    private Boolean hasBathroom;
    private String description;
    private UserResponse user;
}
