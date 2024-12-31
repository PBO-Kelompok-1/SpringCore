package com.tubes.pbo.repositories;

import com.tubes.pbo.models.CheckoutSparepart;
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

    Optional<CheckoutSparepart> findByTransaksiIdAndSparepartId(int transaksiId, int sparepartId);
}
