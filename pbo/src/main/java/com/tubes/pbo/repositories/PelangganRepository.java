package com.tubes.pbo.repositories;

import com.tubes.pbo.models.Pelanggan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PelangganRepository extends JpaRepository<Pelanggan, Integer> {
    
}
