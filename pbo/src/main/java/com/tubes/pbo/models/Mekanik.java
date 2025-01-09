package com.tubes.pbo.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "mekanik")
@PrimaryKeyJoinColumn(name = "user_id") // Foreign key ke tabel User
@Data
@EqualsAndHashCode(callSuper = true) // Menurunkan atribut parent (User)
public class Mekanik extends User {

    @Column(name = "no_telp", length = 15)
    private String noTelp;

    @Column(name = "alamat", length = 255)
    private String alamat;
}
