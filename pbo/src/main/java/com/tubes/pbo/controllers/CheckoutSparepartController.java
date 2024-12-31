package com.tubes.pbo.controllers;

import com.tubes.pbo.models.CheckoutSparepart;
import com.tubes.pbo.models.Sparepart;
import com.tubes.pbo.models.Transaksi;
import com.tubes.pbo.services.CheckoutSparepartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tubes.pbo.repositories.TransaksiRepository;
import com.tubes.pbo.repositories.SparepartRepository;
import com.tubes.pbo.repositories.CheckoutSparepartRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/checkout-spareparts")
public class CheckoutSparepartController {

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private CheckoutSparepartService checkoutSparepartService;

    @Autowired
    private SparepartRepository sparepartRepository;

    @Autowired
    private CheckoutSparepartRepository checkoutSparepartRepository;

    // Add new CheckoutSparepart
    @PostMapping
    public ResponseEntity<CheckoutSparepart> createCheckoutSparepart(@RequestBody CheckoutSparepart checkoutSparepart) {
        // Validasi dan ambil Transaksi dari database
        Optional<Transaksi> transaksiOptional = transaksiRepository.findById(checkoutSparepart.getTransaksi().getId());
        if (transaksiOptional.isEmpty()) {
            return ResponseEntity.status(404).body(null); // Transaksi tidak ditemukan
        }
    
        // Validasi dan ambil Sparepart dari database
        Optional<Sparepart> sparepartOptional = sparepartRepository.findById(checkoutSparepart.getSparepart().getId());
        if (sparepartOptional.isEmpty()) {
            return ResponseEntity.status(404).body(null); // Sparepart tidak ditemukan
        }
    
        Sparepart sparepart = sparepartOptional.get();
        
        // Validasi stok sparepart
        if (sparepart.getStok() < checkoutSparepart.getQuantity()) {
            return ResponseEntity.status(400).body(null); // Stok tidak cukup
        }
    
        // Cek apakah sparepart sudah ada di transaksi
        Optional<CheckoutSparepart> existingCheckoutSparepart = checkoutSparepartRepository
                .findByTransaksiIdAndSparepartId(checkoutSparepart.getTransaksi().getId(), sparepart.getId());
    
        if (existingCheckoutSparepart.isPresent()) {
            // Jika sudah ada, tambahkan quantity sparepart yang ada
            CheckoutSparepart currentCheckoutSparepart = existingCheckoutSparepart.get();
            int updatedQuantity = currentCheckoutSparepart.getQuantity() + checkoutSparepart.getQuantity();
            currentCheckoutSparepart.setQuantity(updatedQuantity);
    
            // Simpan perubahan
            checkoutSparepartRepository.save(currentCheckoutSparepart);
    
            // Kurangi stok sparepart
            int newStok = sparepart.getStok() - checkoutSparepart.getQuantity();
            sparepart.setStok(newStok);
            sparepartRepository.save(sparepart); // Simpan perubahan stok sparepart
    
            return ResponseEntity.ok(currentCheckoutSparepart); // Kembalikan data yang sudah diperbarui
        } else {
            // Jika belum ada, simpan CheckoutSparepart baru
            // Kurangi stok sparepart
            int newStok = sparepart.getStok() - checkoutSparepart.getQuantity();
            sparepart.setStok(newStok);
            sparepartRepository.save(sparepart); // Simpan perubahan stok sparepart
    
            // Set kembali objek transaksi dan sparepart dari database
            checkoutSparepart.setTransaksi(transaksiOptional.get());
            checkoutSparepart.setSparepart(sparepart);
    
            // Simpan CheckoutSparepart baru ke database
            CheckoutSparepart savedCheckoutSparepart = checkoutSparepartService.saveCheckoutSparepart(checkoutSparepart);
            return ResponseEntity.ok(savedCheckoutSparepart);
        }
    }
        
    //  Get all CheckoutSpareparts
    @GetMapping
    public ResponseEntity<List<CheckoutSparepart>> getAllCheckoutSpareparts() {
        List<CheckoutSparepart> list = checkoutSparepartService.getAllCheckoutSpareparts();
        return ResponseEntity.ok(list);
    }

    // Get a CheckoutSparepart by ID
    @GetMapping("/{id}")
    public ResponseEntity<CheckoutSparepart> getCheckoutSparepartById(@PathVariable int id) {
        CheckoutSparepart checkoutSparepart = checkoutSparepartService.getCheckoutSparepartById(id);
        if (checkoutSparepart != null) {
            return ResponseEntity.ok(checkoutSparepart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update a CheckoutSparepart
    @PutMapping("/{id}")
    public ResponseEntity<CheckoutSparepart> updateCheckoutSparepart(
            @PathVariable int id,
            @RequestBody CheckoutSparepart updatedCheckoutSparepart) {
        CheckoutSparepart updated = checkoutSparepartService.updateCheckoutSparepart(id, updatedCheckoutSparepart);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a CheckoutSparepart
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckoutSparepart(@PathVariable int id) {
        boolean deleted = checkoutSparepartService.deleteCheckoutSparepart(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get CheckoutSpareparts by Transaksi ID
    @GetMapping("/by-transaksi/{transaksiId}")
    public ResponseEntity<List<CheckoutSparepart>> getCheckoutSparepartsByTransaksiId(@PathVariable int transaksiId) {
        List<CheckoutSparepart> list = checkoutSparepartService.getCheckoutSparepartsByTransaksiId(transaksiId);
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
