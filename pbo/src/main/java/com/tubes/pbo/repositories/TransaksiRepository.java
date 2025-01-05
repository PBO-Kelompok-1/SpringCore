package com.tubes.pbo.repositories;

import com.tubes.pbo.models.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Date;

@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi, Integer> {
    List<Transaksi> findByMekanik_Username(String username);
    Optional<Transaksi> findById(Long transaksiId);
    List<Transaksi> findByTanggalTransaksiBetween(Date startDate, Date endDate);
    
    // Tambahkan query pencarian berdasarkan nama pelanggan
    List<Transaksi> findByPelanggan_NamaContainingIgnoreCase(String customerName);


}