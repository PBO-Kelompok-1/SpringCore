package com.tubes.pbo;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "dataservice")
@Data
public class dataservice {
    
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "nama")
    private String username;
    @Column(name = "kendaraan")
    private String kendaraan;
}
