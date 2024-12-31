package com.tubes.pbo.repositories;

import com.tubes.pbo.models.Sparepart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SparepartRepository extends JpaRepository<Sparepart, Integer> {
    Optional<Sparepart> findById(Long sparepartId);
    Optional<Sparepart> findByNama(String nama);
}