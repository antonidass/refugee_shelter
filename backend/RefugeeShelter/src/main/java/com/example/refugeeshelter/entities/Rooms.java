package com.example.refugeeshelter.entities;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonFilter("reservationsFilter")
public class Rooms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull(message = "Address should not be a null")
    private String address;

    @Column
    @NotNull(message = "Latitude should not be a null")
    private Double latitude;

    @Column
    @NotNull(message = "Longitude should not be a null")
    private Double longitude;

    @Column
    private Long price;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "has_kitchen")
    private Boolean hasKitchen;

    @Column(name = "has_bathroom")
    private Boolean hasBathroom;

    @Column
    private String description;

    public Rooms(Long id, String address, Double latitude, Double longitude, Long price, String imageUrl, Boolean hasKitchen, Boolean hasBathroom, String description) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.imageUrl = imageUrl;
        this.hasKitchen = hasKitchen;
        this.hasBathroom = hasBathroom;
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User user;

    public void setFields(Rooms newRooms) {
        address = newRooms.getAddress();
        latitude = newRooms.getLatitude();
        longitude = newRooms.getLongitude();
        price = newRooms.getPrice();
        imageUrl = newRooms.getImageUrl();
        hasKitchen = newRooms.getHasKitchen();
        hasBathroom = newRooms.getHasBathroom();
        description = newRooms.getDescription();
    }

}
