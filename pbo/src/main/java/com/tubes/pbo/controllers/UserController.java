package com.tubes.pbo.controllers;

import com.tubes.pbo.models.User;
import com.tubes.pbo.models.Mekanik;
import com.tubes.pbo.repositories.UserRepository;
import com.tubes.pbo.repositories.MekanikRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MekanikRepository mekanikRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Menambahkan data mekanik (Create)
    @PostMapping("/add")
    public String addMekanik(@RequestBody Mekanik newMekanik) {
        String encryptedPassword = passwordEncoder.encode(newMekanik.getPassword());

        newMekanik.setPassword(encryptedPassword);

        mekanikRepository.save(newMekanik);
        return "Mekanik berhasil ditambahkan!";
    }

    // Mengambil semua data mekanik (Read)
    @GetMapping("/all")
    public List<Mekanik> getAllMekanik() {
        return mekanikRepository.findAll();
    }

    // Mengambil data user berdasarkan ID (Read)
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable int id) {
        return Optional.ofNullable(userRepository.findById(id));
    }

    // Mengambil data user berdasarkan username (Read)
    @GetMapping("/username/{username}")
    public Optional<User> getUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }

    // Menghapus user berdasarkan ID (Delete)
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        mekanikRepository.deleteById(id);
        return "Mekanik dengan ID " + id + " berhasil dihapus!";
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMekanik(@PathVariable int id, @RequestBody Mekanik updatedMekanik) {
        Mekanik existingMekanik = mekanikRepository.findById(id);
        if (existingMekanik != null) {
            existingMekanik.setUsername(updatedMekanik.getUsername());
            existingMekanik.setNoTelp(updatedMekanik.getNoTelp());
            existingMekanik.setAlamat(updatedMekanik.getAlamat());
            String encryptedPassword = passwordEncoder.encode(updatedMekanik.getPassword());
            existingMekanik.setPassword(encryptedPassword);
            mekanikRepository.save(existingMekanik);
            return ResponseEntity.ok("Mekanik updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    

}
