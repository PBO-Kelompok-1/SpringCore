package com.tubes.pbo.models;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pelanggan")
@Data
public class pelanggan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "nama")
    private String nama;
    @Column(name = "kendaraan")
    private String kendaraan;
    @Column(name = "no_polisi")
    private String no_polisi;
    @Column(name = "alamat")
    private String alamat;
    @Column(name = "no_hp")
    private String no_hp;
    @Column(name = "catatan")
    private String catatan;
}
