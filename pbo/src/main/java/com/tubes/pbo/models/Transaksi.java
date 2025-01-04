// filepath: /d:/KULIAH/Semester 5/TubesPBO/SpringCore/pbo/src/main/java/com/tubes/pbo/models/Transaksi.java
package com.tubes.pbo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
  @Column(name = "stnk")
  private String stnk;
  
  @NotBlank
  @Column(name = "motor")
  private String motor;
  
  @Column(name = "biaya_jasa")
  private double biayaJasa;
  
  @Column(name = "status")
  private String status;

  @ManyToOne
  @JoinColumn(name = "id_pelanggan", referencedColumnName = "id")
  private Pelanggan pelanggan;
  
  @ManyToOne
  @JoinColumn(name = "id_mekanik", referencedColumnName = "id")
  private User mekanik;

  @Column(name = "tanggal_transaksi")
  @Temporal(TemporalType.DATE)
  private Date tanggalTransaksi = new Date();
  
}