package com.example.refugeeshelter.dto.response;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonFilter("roomsFilter")
@Builder
public class RoomsDTO {
  private Long id;
  private String address;
  private Double latitude;
  private Double longitude;
  private Long price;
  private String imageUrl;
  private Boolean hasKitchen;
  private Boolean hasBathroom;
  private String description;
  private UserDTO user;
}
