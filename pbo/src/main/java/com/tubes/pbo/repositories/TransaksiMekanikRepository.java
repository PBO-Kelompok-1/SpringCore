package com.tubes.pbo.repositories;

import com.tubes.pbo.models.CheckoutSparepart;
import com.tubes.pbo.models.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaksiMekanikRepository extends JpaRepository<CheckoutSparepart, Integer> {
    // method untuk mencari transaksi sparepart berdasarkan daftar transaksi
    List<CheckoutSparepart> findAllByTransaksiIn(List<Transaksi> transaksiList);
}
