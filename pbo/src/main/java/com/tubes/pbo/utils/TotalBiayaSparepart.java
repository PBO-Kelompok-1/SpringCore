package com.tubes.pbo.utils;

import com.tubes.pbo.models.CheckoutSparepart;
import com.tubes.pbo.repositories.CheckoutSparepartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TotalBiayaSparepart {

    private int totalBiaya = 0; // Awalnya total biaya adalah 0

    @Autowired
    private CheckoutSparepartRepository checkoutSparepartRepository;

    // Method untuk menghitung total biaya sparepart
    public void calculateTotalBiayaSparepart() {
        List<CheckoutSparepart> checkoutSparepartList = checkoutSparepartRepository.findAll();
        int total = 0;

        // Loop untuk menghitung total biaya sparepart
        for (CheckoutSparepart checkoutSparepart : checkoutSparepartList) {
            System.out.println("MASUK LOOPING");
            int quantity = checkoutSparepart.getQuantity();
            double harga = checkoutSparepart.getSparepart().getHarga();
            double biaya = quantity * harga;
            int transaction_id = checkoutSparepart.getTransaksi().getId();

            // Debugging log
            System.out.println("Quantity: " + quantity + ", Harga: " + harga + ", Biaya: " + biaya);
            System.out.println("Transaction ID: " + transaction_id);

            total += biaya;
        }

        System.out.println("KELUAR LOOPING");
        this.totalBiaya = total;  // Menyimpan total biaya sparepart
    }

    // Method untuk mengambil total biaya sparepart (memanggil perhitungan jika belum dilakukan)
    public synchronized int getTotalBiayaSparepart() {
        calculateTotalBiayaSparepart(); // Pastikan perhitungan dilakukan terlebih dahulu
        return this.totalBiaya; // Mengembalikan nilai total biaya
    }
}
