package com.tubes.pbo.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED) // Pewarisan
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false, length = 20)
    private String role;
}
