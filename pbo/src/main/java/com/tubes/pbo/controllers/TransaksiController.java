package com.tubes.pbo.controllers;

import com.tubes.pbo.models.Transaksi;
import com.tubes.pbo.models.Pelanggan;
import com.tubes.pbo.models.User;
import com.tubes.pbo.repositories.TransaksiRepository;
import com.tubes.pbo.repositories.PelangganRepository;
import com.tubes.pbo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/transaksi")
public class TransaksiController {

  @Autowired
  private TransaksiRepository transaksiRepository;

  @Autowired
  private PelangganRepository pelangganRepository;

  @Autowired
  private UserRepository userRepository;

  @PostMapping
  public ResponseEntity<String> addTransaksi(@RequestBody Transaksi newTransaksi) {
    transaksiRepository.save(newTransaksi);
    return ResponseEntity.ok("Transaksi berhasil ditambahkan.");
  }  

  @GetMapping("/all")
  public List<Transaksi> getAllTransaksi() {
      return transaksiRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Transaksi> getTransaksiById(@PathVariable int id) {
    Optional<Transaksi> transaksi = transaksiRepository.findById(id);
    return transaksi.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateTransaksi(@PathVariable int id, @RequestBody Transaksi updatedTransaksi) {
    Optional<Transaksi> existingTransaksi = transaksiRepository.findById(id);
    if (existingTransaksi.isPresent()) {
      Transaksi transaksi = existingTransaksi.get();
      transaksi.setCatatan(updatedTransaksi.getCatatan());
      transaksi.setStnk(updatedTransaksi.getStnk());
      transaksi.setMotor(updatedTransaksi.getMotor());
      transaksi.setSparepart(updatedTransaksi.getSparepart());
      transaksi.setBiayaJasa(updatedTransaksi.getBiayaJasa());
      transaksi.setPelanggan(updatedTransaksi.getPelanggan());
      transaksi.setMekanik(updatedTransaksi.getMekanik());
      transaksiRepository.save(transaksi);
      return ResponseEntity.ok("Transaksi berhasil diperbarui.");
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteTransaksi(@PathVariable int id) {
    if (!transaksiRepository.existsById(id)) {
      return ResponseEntity.status(404).body("Transaksi dengan ID " + id + " tidak ditemukan.");
    }
    transaksiRepository.deleteById(id);
    return ResponseEntity.ok("Transaksi dengan ID " + id + " berhasil dihapus.");
  }

  @GetMapping("/pelanggan/{id}")
  public ResponseEntity<Pelanggan> getPelangganById(@PathVariable int id) {
    Optional<Pelanggan> pelanggan = pelangganRepository.findById(id);
    return pelanggan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/mekanik/{id}")
  public ResponseEntity<User> getMekanikByIdAndRole(@PathVariable int id) {
    User mekanik = userRepository.findById(id);
    if (mekanik != null && "MEKANIK".equals(mekanik.getRole())) {
      return ResponseEntity.ok(mekanik);
    } else {
      return ResponseEntity.notFound().build();
    }
  }


  
}
