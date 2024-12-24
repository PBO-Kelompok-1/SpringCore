package com.tubes.pbo.models;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.Date;


@Entity
@Table(name = "transaksi")
public class Transaksi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    private Pelanggan pelanggan;
    
    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    private Mekanik mekanik;

    @JoinColumn(name = "id", referencedColumnName = "id")
    private List<Sparepart> sparepart;
    @Column(name = "tarif", nullable = false)
    private double tarif;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "waktuTransaksi", nullable = false)
    private Date waktuTransaksi;
}
