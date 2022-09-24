package com.example.refugeeshelter.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String phone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "roles_id", referencedColumnName = "id", nullable = false, updatable = false)
            })
    private Collection<Role> roles = new ArrayList<>();
}
