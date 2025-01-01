package com.tubes.pbo.models;
import jakarta.persistence.*; 
import jakarta.validation.constraints.*;
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
    @NotNull(message = "Harga tidak boleh kosong.")
    @Positive(message = "Harga harus lebih besar dari 0.")
    private double harga;
    
    @Column(name = "stok")
    @NotNull(message = "Stok tidak boleh kosong.") 
    @Min(value = 0, message = "Stok harus minimal 0.")
    private int stok;
}