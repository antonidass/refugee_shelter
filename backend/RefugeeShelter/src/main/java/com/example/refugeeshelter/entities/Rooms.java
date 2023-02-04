package com.example.refugeeshelter.entities;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonFilter("reservationsFilter")
public class Rooms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull(message = "Name should not be a null")
    private String name;

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

    @Column(name = "beds")
    private Long beds;

    @Column(name = "people")
    private Long people;

    @Column
    private String description;

    public Rooms(Long id, String address, Double latitude, Double longitude, Long price, String imageUrl, Long beds, Long people, String description, String name) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.imageUrl = imageUrl;
        this.beds = beds;
        this.people = people;
        this.description = description;
        this.name = name;
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
        beds = newRooms.getBeds();
        people = newRooms.getPeople();
        description = newRooms.getDescription();
        name = newRooms.getName();
    }

}
