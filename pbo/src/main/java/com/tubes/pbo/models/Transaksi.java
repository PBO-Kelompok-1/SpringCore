// filepath: /d:/KULIAH/Semester 5/TubesPBO/SpringCore/pbo/src/main/java/com/tubes/pbo/models/Transaksi.java
package com.tubes.pbo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

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
  
  @NotBlank
  @Column(name = "sparepart")
  private String sparepart;
  
  @Column(name = "biaya_jasa")
  private double biayaJasa;
  
  @ManyToOne
  @JoinColumn(name = "id_pelanggan", referencedColumnName = "id")
  private Pelanggan pelanggan;
  
  @ManyToOne
  @JoinColumn(name = "id_mekanik", referencedColumnName = "id")
  private User mekanik;

  public void setPelangganId(int pelangganId) {
    this.pelanggan = new Pelanggan();
    this.pelanggan.setId(pelangganId);
  }

  public void setMekanikId(int mekanikId) {
    this.mekanik = new User();
    this.mekanik.setId((long) mekanikId);
  }
}