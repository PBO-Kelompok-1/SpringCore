// filepath: /d:/KULIAH/Semester 5/TubesPBO/SpringCore/pbo/src/main/java/com/tubes/pbo/models/Transaksi.java
package com.tubes.pbo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "transaksi")
@Data
public class Transaksi {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;
  
  @NotBlank
  @Column(name = "catatan")
  private String catatan;
  
  @NotBlank
  @Column(name = "motor")
  private String motor;
  
  @NotNull
  @Column(name = "biaya_jasa")
  private double biayaJasa;

  @NotBlank
  @Column(name = "status")
  private String status;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "id_pelanggan", referencedColumnName = "id")
  private Pelanggan pelanggan;
  
  @NotNull
  @ManyToOne
  @JoinColumn(name = "id_mekanik", referencedColumnName = "id")
  private User mekanik;

  @NotNull
  @Column(name = "tanggal_transaksi")
  @Temporal(TemporalType.DATE)
  private Date tanggalTransaksi = new Date();
  
}