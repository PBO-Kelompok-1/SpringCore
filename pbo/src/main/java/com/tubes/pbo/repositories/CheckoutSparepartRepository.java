package com.tubes.pbo.repositories;

import com.tubes.pbo.models.CheckoutSparepart;
import com.tubes.pbo.models.Pelanggan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckoutSparepartRepository extends JpaRepository<CheckoutSparepart, Integer> {
    @Query("SELECT c FROM CheckoutSparepart c WHERE c.transaksi.id = :transaksiId")
    List<CheckoutSparepart> findByTransaksiId(@Param("transaksiId") int transaksiId);

    @Query("SELECT c FROM CheckoutSparepart c WHERE c.sparepart.id = :sparepartId")
    List<CheckoutSparepart> findBySparepartId(@Param("sparepartId") int sparepartId);

    @Query("SELECT c.transaksi.pelanggan FROM CheckoutSparepart c WHERE c.sparepart.nama = :namaSparepart")
    List<Pelanggan> findPelangganBySparepartNama(@Param("namaSparepart") String namaSparepart);

    Optional<CheckoutSparepart> findByTransaksiIdAndSparepartId(int transaksiId, int sparepartId);
}
