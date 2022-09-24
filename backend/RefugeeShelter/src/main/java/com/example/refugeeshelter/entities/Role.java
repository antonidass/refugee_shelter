package com.example.refugeeshelter.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Table
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Collection<User> users = new ArrayList<>();
}
