package com.tubes.pbo.models;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Table(name = "mekanik")
public class Mekanik extends User{
    @Column(name = "shift_jaga", length = 50)
    private String shiftJaga;
    @Column(name = "gaji")
    private double gaji;
    @Column(name = "bonus")
    private double bonus;
}
