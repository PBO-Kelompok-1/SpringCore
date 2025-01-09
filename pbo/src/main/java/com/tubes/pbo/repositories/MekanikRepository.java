package com.tubes.pbo.repositories;
import com.tubes.pbo.models.Mekanik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MekanikRepository extends JpaRepository<Mekanik, Long> {
    Mekanik findById(int id);
}