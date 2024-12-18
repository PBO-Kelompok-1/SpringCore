package com.tubes.pbo.repositories;

import com.tubes.pbo.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<user, Integer> {

    List<user> findByUsername(String username);

    user findById(int id);
}
