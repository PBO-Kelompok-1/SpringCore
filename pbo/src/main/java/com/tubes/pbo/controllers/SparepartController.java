package com.tubes.pbo.controllers;

import com.tubes.pbo.models.Sparepart;
import com.tubes.pbo.repositories.SparepartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sparepart")
public class SparepartController {

    @Autowired
    private SparepartRepository sparepartRepository;

    @PostMapping
    public ResponseEntity<String> addSparepart(@RequestBody Sparepart newSparepart /* , Authentication authentication */) {
        // if (!authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
        //     return ResponseEntity.status(403).body("Access Denied: Only admin can add data.");
        // }
        sparepartRepository.save(newSparepart);
        return ResponseEntity.ok("Sparepart added successfully.");
    }

    // Get all sparepart
    @GetMapping
    public List<Sparepart> getAllSpareparts() {
        return sparepartRepository.findAll();
    }

    // Get sparepart by ID
    @GetMapping("/{id}")
    public ResponseEntity<Sparepart> getSparepartById(@PathVariable int id) {
        Optional<Sparepart> sparepart = sparepartRepository.findById(id);
        return sparepart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update sparepart
    @PutMapping("/{id}")
    public ResponseEntity<String> updateSparepart(@PathVariable int id, @RequestBody Sparepart updatedSparepart) {
        Optional<Sparepart> existingSparepart = sparepartRepository.findById(id);
        if (existingSparepart.isPresent()) {
            Sparepart sparepart = existingSparepart.get();
            sparepart.setNama(updatedSparepart.getNama());
            sparepart.setDeskripsi(updatedSparepart.getDeskripsi());
            sparepart.setHarga(updatedSparepart.getHarga());
            sparepart.setStok(updatedSparepart.getStok());
            sparepartRepository.save(sparepart);
            return ResponseEntity.ok("Sparepart updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSparepart(@PathVariable int id /* , Authentication authentication */) {
        // Periksa apakah user memiliki role ADMIN
        // if (!authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
        //     return ResponseEntity.status(403).body("Access Denied: Only admin can delete data.");
        // }
        
        // Periksa apakah sparepart dengan ID yang diberikan ada
        if (!sparepartRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Sparepart with ID " + id + " not found.");
        }
        
        // Hapus sparepart
        sparepartRepository.deleteById(id);
        return ResponseEntity.ok("Sparepart with ID " + id + " deleted successfully.");
    }
}
