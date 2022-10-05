package com.example.refugeeshelter.entities;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

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
    private String address;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private Long price;

    @Column(name = "has_kitchen")
    private Boolean hasKitchen;

    @Column(name = "has_bathroom")
    private Boolean hasBathroom;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User user;

    public void setFields(Rooms newRooms) {
        address = newRooms.getAddress();
        latitude = newRooms.getLatitude();
        longitude = newRooms.getLongitude();
        price = newRooms.getPrice();
        hasKitchen = newRooms.getHasKitchen();
        hasBathroom = newRooms.getHasBathroom();
        description = newRooms.getDescription();
        user = newRooms.getUser();
    }

}
