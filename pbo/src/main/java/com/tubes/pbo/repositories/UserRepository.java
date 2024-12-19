package com.tubes.pbo.repositories;

import com.tubes.pbo.models.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<user, Integer> {

    Optional<user> findByUsername(String username);

    user findById(int id);
}



