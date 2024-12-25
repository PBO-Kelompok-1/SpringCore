package com.tubes.pbo.repositories;

import com.tubes.pbo.models.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi, Integer> {
}
