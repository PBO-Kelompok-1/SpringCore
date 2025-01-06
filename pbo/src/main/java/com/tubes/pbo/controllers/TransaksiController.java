package com.tubes.pbo.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tubes.pbo.models.CheckoutSparepart;
import com.tubes.pbo.models.Pelanggan;
import com.tubes.pbo.models.Sparepart;
import com.tubes.pbo.models.Transaksi;
import com.tubes.pbo.models.User;
import com.tubes.pbo.repositories.CheckoutSparepartRepository;
import com.tubes.pbo.repositories.PelangganRepository;
import com.tubes.pbo.repositories.SparepartRepository;
import com.tubes.pbo.repositories.TransaksiRepository;
import com.tubes.pbo.repositories.UserRepository;

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

      // Menyusun sparepart dalam format yang sesuai dan menghitung total harga
      // sparepart
      double totalHargaSparepart = checkoutSpareparts.stream()
          .mapToDouble(cs -> cs.getQuantity() * cs.getSparepart().getHarga())
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

  // @PostMapping("/add-transaction")
  // public ResponseEntity<String> addTransaction(@RequestBody Transaksi
  // transaksi, @RequestParam List<Integer> sparepartIds) {
  // // 1. Save the Transaksi
  // transaksiRepository.save(transaksi);

  // // 2. For each sparepart ID, create a CheckoutSparepart
  // for (Integer spId : sparepartIds) {
  // Sparepart sparepart = sparepartRepository.findById(spId).orElse(null);
  // if (sparepart != null) {
  // CheckoutSparepart checkout = new CheckoutSparepart();
  // checkout.setTransaksi(transaksi);
  // checkout.setSparepart(sparepart);
  // checkout.setQuantity(1); // or a chosen quantity
  // checkoutSparepartRepository.save(checkout);
  // }
  // }
  // return ResponseEntity.ok("Transaksi beserta Sparepart berhasil disimpan.");
  // }

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

  @GetMapping("/search-by-date")
  public ResponseEntity<List<Map<String, Object>>> getTransaksiByDateRange(
      @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
      @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
    List<Transaksi> transaksiList = transaksiRepository.findByTanggalTransaksiBetween(startDate, endDate);
    List<Map<String, Object>> responseList = transaksiList.stream().map(transaksi -> {
      Map<String, Object> response = new HashMap<>();
      response.put("id", transaksi.getId());
      response.put("pelanggan", transaksi.getPelanggan());
      response.put("mekanik", transaksi.getMekanik());
      response.put("stnk", transaksi.getStnk());
      response.put("motor", transaksi.getMotor());
      response.put("catatan", transaksi.getCatatan());
      response.put("biayaJasa", transaksi.getBiayaJasa());
      List<CheckoutSparepart> checkoutSpareparts = checkoutSparepartRepository.findByTransaksiId(transaksi.getId());
      response.put("totalHarga", transaksi.getBiayaJasa() + checkoutSpareparts.stream()
          .mapToDouble(cs -> cs.getQuantity() * cs.getSparepart().getHarga()).sum());
      response.put("status", transaksi.getStatus());
      return response;
    }).collect(Collectors.toList());
    return ResponseEntity.ok(responseList);
  }

}
