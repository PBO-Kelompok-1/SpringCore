package com.tubes.pbo.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "sparepart")
@Data

public class Sparepart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nama")
    @NotBlank(message = "Nama tidak boleh kosong.")
    private String nama;

    @Column(name = "deskripsi")
    private String deskripsi;

    @Column(name = "harga")
    @NotBlank(message = "Harga tidak boleh kosong.")
    private String harga;
    
    @Column(name = "stok")
    @NotBlank(message = "Stok tidak boleh kosong.")
    private String stok;
}