package com.tubes.pbo.models;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pelanggan")
@Data

public class Pelanggan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "nama")
    private String nama;
    @Column(name = "alamat")
    private String alamat;
    @Column(name = "no_hp")
    private String no_hp;
}
