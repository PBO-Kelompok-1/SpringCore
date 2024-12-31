package com.tubes.pbo.repositories;

import com.tubes.pbo.models.Sparepart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SparepartRepository extends JpaRepository<Sparepart, Integer> {
    Optional<Sparepart> findById(Long sparepartId);
}