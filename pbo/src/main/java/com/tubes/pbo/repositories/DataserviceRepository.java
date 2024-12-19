package com.tubes.pbo.repositories;

import com.tubes.pbo.models.dataservice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataserviceRepository extends JpaRepository<dataservice, Integer> {
}