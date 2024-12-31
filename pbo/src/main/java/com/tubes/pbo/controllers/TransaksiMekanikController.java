package com.tubes.pbo.controllers;

import com.tubes.pbo.models.Transaksi;
import com.tubes.pbo.models.CheckoutSparepart;
import com.tubes.pbo.models.Sparepart;
import com.tubes.pbo.repositories.TransaksiRepository;
import com.tubes.pbo.repositories.TransaksiMekanikRepository;
import com.tubes.pbo.repositories.CheckoutSparepartRepository;
import com.tubes.pbo.repositories.SparepartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
public class TransaksiMekanikController {

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private TransaksiMekanikRepository transaksiMekanikRepository;

    @Autowired
    private SparepartRepository sparepartRepository;
    
    @Autowired
    private CheckoutSparepartRepository checkoutSparepartRepository;

    @GetMapping("/dashboard-mekanik")
    public String dashboardMekanik(Model model) {
        // Mendapatkan username yang sedang login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Mendapatkan semua transaksi yang memiliki id_mekanik yang sesuai dengan username
        List<Transaksi> transaksiList = transaksiRepository.findByMekanik_Username(username);

        // Mendapatkan data transaksi sparepart terkait transaksi yang ditemukan
        List<CheckoutSparepart> transaksiSparepartList = transaksiMekanikRepository.findAllByTransaksiIn(transaksiList);

        // Mendapatkan semua data sparepart
        List<Sparepart> spareparts = sparepartRepository.findAll();

        // Membuat map untuk menyimpan biayaTotal per transaksi
        Map<Integer, Double> biayaTotalMap = new HashMap<>();

        // Menghitung biaya total untuk setiap transaksi
        for (Transaksi transaksi : transaksiList) {
            double biayaTotal = transaksi.getBiayaJasa();
            for (CheckoutSparepart checkoutSparepart : transaksiSparepartList) {
                if (checkoutSparepart.getTransaksi().getId() == transaksi.getId()) {
                    biayaTotal += checkoutSparepart.getQuantity() * checkoutSparepart.getSparepart().getHarga();
                }
            }
            biayaTotalMap.put(transaksi.getId(), biayaTotal);
        }

        model.addAttribute("username", username);
        model.addAttribute("spareparts", spareparts);
        model.addAttribute("transaksiList", transaksiList);
        model.addAttribute("transaksiSparepartList", transaksiSparepartList);
        model.addAttribute("biayaTotalMap", biayaTotalMap);


        return "dashboard-mekanik"; 
    }
    @PostMapping("/mekanik/add-sparepart")
    public String insertCheckoutSparepart(
            @RequestParam("id-transaksi") Long transaksiId,
            @RequestParam("id-sparepart") Long sparepartId,
            @RequestParam("quantity") Integer quantity,
            Model model) {
        
        // Cari transaksi berdasarkan ID
        Transaksi transaksi = transaksiRepository.findById(transaksiId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid transaksi ID: " + transaksiId));
        
        // Cari sparepart berdasarkan ID
        Sparepart sparepart = sparepartRepository.findById(sparepartId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sparepart ID: " + sparepartId));
        
        // Validasi stok mencukupi
        if (sparepart.getStok() < quantity) {
            throw new IllegalArgumentException("Stok sparepart tidak mencukupi");
        }
        
        // Cek apakah sudah ada sparepart yang sama dalam transaksi ini
        Optional<CheckoutSparepart> existingCheckoutSparepart = checkoutSparepartRepository.findByTransaksiIdAndSparepartId(transaksiId.intValue(), sparepartId.intValue());
    
        if (existingCheckoutSparepart.isPresent()) {
            // Jika sudah ada, update jumlahnya (quantity)
            CheckoutSparepart checkoutSparepart = existingCheckoutSparepart.get();
            checkoutSparepart.setQuantity(checkoutSparepart.getQuantity() + quantity);
    
            // Kurangi stok sparepart
            sparepart.setStok(sparepart.getStok() - quantity);
            sparepartRepository.save(sparepart); // Simpan perubahan stok ke database
    
            // Simpan perubahan CheckoutSparepart yang sudah diupdate
            checkoutSparepartRepository.save(checkoutSparepart);
        } else {
            // Jika belum ada, buat entri baru
            // Kurangi stok sparepart
            sparepart.setStok(sparepart.getStok() - quantity);
            sparepartRepository.save(sparepart); // Simpan perubahan stok ke database
    
            // Buat objek CheckoutSparepart baru
            CheckoutSparepart checkoutSparepart = new CheckoutSparepart();
            checkoutSparepart.setTransaksi(transaksi);
            checkoutSparepart.setSparepart(sparepart);
            checkoutSparepart.setQuantity(quantity);
    
            checkoutSparepartRepository.save(checkoutSparepart);
        }

        return "redirect:/dashboard-mekanik";
    }

    @PutMapping("/transaksi-selesai/{id}")
    public String selesaikanTransaksi(@PathVariable("id") Long transaksiId, Model model) {
        // Cari transaksi berdasarkan ID
        Transaksi transaksi = transaksiRepository.findById(transaksiId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid transaksi ID: " + transaksiId));
        
        transaksi.setStatus("done");
        transaksiRepository.save(transaksi);

        return "redirect:/dashboard-mekanik";
    }

}