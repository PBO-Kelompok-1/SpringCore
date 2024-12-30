package com.tubes.pbo.controllers;

import com.tubes.pbo.models.Transaksi;
import com.tubes.pbo.models.Pelanggan;
import com.tubes.pbo.models.User;
import com.tubes.pbo.models.CheckoutSparepart;
import com.tubes.pbo.repositories.TransaksiRepository;
import com.tubes.pbo.repositories.PelangganRepository;
import com.tubes.pbo.repositories.CheckoutSparepartRepository;
import com.tubes.pbo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;


@RestController
@RequestMapping("/transaksi")
public class TransaksiController {

  @Autowired
  private TransaksiRepository transaksiRepository;

  @Autowired
  private PelangganRepository pelangganRepository;

  @Autowired
  private CheckoutSparepartRepository checkoutSparepartRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SparepartRepository sparepartRepository;

  @PostMapping
  public String addTransaksi(@RequestBody Transaksi transaksi) {
      transaksiRepository.save(transaksi);
      return "redirect:/transaksi";
  }

  @GetMapping("/all")
  public List<Map<String, Object>> getAllTransaksi() {
      List<Transaksi> transaksiList = transaksiRepository.findAll();
  
      return transaksiList.stream().map(transaksi -> {
          // Menyusun response untuk tiap transaksi
          Map<String, Object> response = new HashMap<>();
          response.put("id", transaksi.getId());
          response.put("catatan", transaksi.getCatatan());
          response.put("stnk", transaksi.getStnk());
          response.put("motor", transaksi.getMotor());
          response.put("biayaJasa", transaksi.getBiayaJasa());
          response.put("pelanggan", transaksi.getPelanggan());
          response.put("mekanik", transaksi.getMekanik());
          response.put("status", transaksi.getStatus());

          // Ambil daftar sparepart yang terkait dengan transaksi ini
          List<CheckoutSparepart> checkoutSpareparts = checkoutSparepartRepository.findByTransaksiId(transaksi.getId());
  
          // Menyusun sparepart dalam format yang sesuai dan menghitung total harga sparepart
          double totalHargaSparepart = checkoutSpareparts.stream()
                  .mapToDouble(cs -> cs.getQuantity() * Double.parseDouble(cs.getSparepart().getHarga()))
                  .sum(); // Total harga sparepart
          
          // Menyusun list sparepart
          List<Map<String, Object>> spareparts = checkoutSpareparts.stream().map(cs -> {
              Map<String, Object> sparepart = new HashMap<>();
              sparepart.put("id", cs.getSparepart().getId());
              sparepart.put("nama", cs.getSparepart().getNama());
              sparepart.put("deskripsi", cs.getSparepart().getDeskripsi());
              sparepart.put("harga", cs.getSparepart().getHarga());
              sparepart.put("quantity", cs.getQuantity());
              return sparepart;
          }).collect(Collectors.toList());
  
          // Menambahkan total harga sparepart ke response
          response.put("spareparts", spareparts);
  
          // Menghitung total harga transaksi: biaya jasa + total harga sparepart
          double totalHarga = transaksi.getBiayaJasa() + totalHargaSparepart;
          response.put("totalHarga", totalHarga);
  
          return response;
      }).collect(Collectors.toList());
  }
  
  

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getTransaksiWithSpareparts(@PathVariable int id) {
      Optional<Transaksi> transaksiOptional = transaksiRepository.findById(id);
      if (transaksiOptional.isEmpty()) {
          return ResponseEntity.notFound().build();
      }

      Transaksi transaksi = transaksiOptional.get();
      
      // Ambil daftar sparepart dari CheckoutSparepart yang terkait
      List<CheckoutSparepart> checkoutSpareparts = checkoutSparepartRepository.findByTransaksiId(id);

      // Menyusun respon dalam format yang diinginkan
      Map<String, Object> response = new HashMap<>();
      response.put("id", transaksi.getId());
      response.put("catatan", transaksi.getCatatan());
      response.put("stnk", transaksi.getStnk());
      response.put("motor", transaksi.getMotor());
      response.put("biayaJasa", transaksi.getBiayaJasa());
      response.put("pelanggan", transaksi.getPelanggan());
      response.put("mekanik", transaksi.getMekanik());
      response.put("status", transaksi.getStatus());

      // Mengonversi CheckoutSparepart ke format yang lebih sederhana
      List<Map<String, Object>> spareparts = checkoutSpareparts.stream().map(cs -> {
          Map<String, Object> sparepart = new HashMap<>();
          sparepart.put("id", cs.getSparepart().getId());
          sparepart.put("nama", cs.getSparepart().getNama());
          sparepart.put("deskripsi", cs.getSparepart().getDeskripsi());
          sparepart.put("harga", cs.getSparepart().getHarga());
          sparepart.put("quantity", cs.getQuantity());
          return sparepart;
      }).collect(Collectors.toList());

      response.put("spareparts", spareparts);

      return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateTransaksi(@PathVariable int id, @RequestBody Transaksi updatedTransaksi) {
      Optional<Transaksi> existingTransaksi = transaksiRepository.findById(id);
      if (existingTransaksi.isPresent()) {
          Transaksi transaksi = existingTransaksi.get();
          transaksi.setCatatan(updatedTransaksi.getCatatan());
          transaksi.setStnk(updatedTransaksi.getStnk());
          transaksi.setMotor(updatedTransaksi.getMotor());
          transaksi.setBiayaJasa(updatedTransaksi.getBiayaJasa());
          transaksi.setPelanggan(updatedTransaksi.getPelanggan());
          transaksi.setMekanik(updatedTransaksi.getMekanik());
          transaksi.setStatus(updatedTransaksi.getStatus());
          transaksiRepository.save(transaksi);
          return ResponseEntity.ok("Transaksi berhasil diperbarui.");
      } else {
          return ResponseEntity.notFound().build();
      }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteTransaksi(@PathVariable int id) {
    if (transaksiRepository.existsById(id)) {
      transaksiRepository.deleteById(id);
      return ResponseEntity.ok("Transaksi berhasil dihapus.");
    } else {
      return ResponseEntity.notFound().build();
    }
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
  // Inner class untuk menggabungkan data transaksi dan sparepart
  public static class TransaksiWithSpareparts {
    private Transaksi transaksi;
    private List<CheckoutSparepart> spareparts;

    public TransaksiWithSpareparts(Transaksi transaksi, List<CheckoutSparepart> spareparts) {
        this.transaksi = transaksi;
        this.spareparts = spareparts;
     }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    public List<CheckoutSparepart> getSpareparts() {
        return spareparts;
    }

    public void setSpareparts(List<CheckoutSparepart> spareparts) {
        this.spareparts = spareparts;
    }
  }
}
