// Controller for Pelanggan
package com.tubes.pbo.controllers;

import com.tubes.pbo.models.pelanggan;
import com.tubes.pbo.repositories.PelangganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pelanggan")
public class PelangganController {

    @Autowired
    private PelangganRepository pelangganRepository;

    @PostMapping
    public ResponseEntity<String> addPelanggan(@RequestBody pelanggan newPelanggan /* , Authentication authentication */) {
        // if (!authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
        //     return ResponseEntity.status(403).body("Access Denied: Only admin can add data.");
        // }
        pelangganRepository.save(newPelanggan);
        return ResponseEntity.ok("Pelanggan added successfully.");
    }

    // Get all pelanggan
    @GetMapping
    public List<pelanggan> getAllPelanggan() {
        return pelangganRepository.findAll();
    }

    // Get pelanggan by ID
    @GetMapping("/{id}")
    public ResponseEntity<pelanggan> getPelangganById(@PathVariable int id) {
        Optional<pelanggan> pelanggan = pelangganRepository.findById(id);
        return pelanggan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update pelanggan (only accessible by admin)
    @PutMapping("/{id}")
    public ResponseEntity<String> updatePelanggan(@PathVariable int id, @RequestBody pelanggan updatedPelanggan /*, Authentication authentication */) {
        // if (!authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
        //     return ResponseEntity.status(403).body("Access Denied: Only admin can edit data.");
        // }

        Optional<pelanggan> existingPelanggan = pelangganRepository.findById(id);
        if (existingPelanggan.isPresent()) {
            pelanggan pelanggan = existingPelanggan.get();
            pelanggan.setNama(updatedPelanggan.getNama());
            pelanggan.setKendaraan(updatedPelanggan.getKendaraan());
            pelanggan.setNo_polisi(updatedPelanggan.getNo_polisi());
            pelanggan.setAlamat(updatedPelanggan.getAlamat());
            pelanggan.setNo_hp(updatedPelanggan.getNo_hp());
            pelanggan.setCatatan(updatedPelanggan.getCatatan());
            pelangganRepository.save(pelanggan);
            return ResponseEntity.ok("Pelanggan updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePelanggan(@PathVariable int id /* , Authentication authentication */) {
        // Periksa apakah user memiliki role ADMIN
        // if (!authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
        //     return ResponseEntity.status(403).body("Access Denied: Only admin can delete data.");
        // }
        
        // Periksa apakah pelanggan dengan ID yang diberikan ada
        if (!pelangganRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Pelanggan with ID " + id + " not found.");
        }
        
        // Hapus pelanggan
        pelangganRepository.deleteById(id);
        return ResponseEntity.ok("Pelanggan with ID " + id + " deleted successfully.");
    }
}
