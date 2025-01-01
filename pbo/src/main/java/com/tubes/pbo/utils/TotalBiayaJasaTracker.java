package com.tubes.pbo.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tubes.pbo.models.Transaksi;
import com.tubes.pbo.repositories.TransaksiRepository;

@Component
public class TotalBiayaJasaTracker {

    private int totalBiaya = 0;

    @Autowired
    private TransaksiRepository transaksiRepository; // Autowired field injection

    // Method to calculate total service costs
    private int calculateTotalBiaya() {
        int total = 0;
    
        // Get all transactions
        List<Transaksi> transaksiList = transaksiRepository.findAll();
    
        // Loop through and add up the service cost from each transaction
        for (Transaksi transaksi : transaksiList) {
            System.out.println("Biaya Jasa: " + transaksi.getBiayaJasa());
            total += transaksi.getBiayaJasa();
            System.out.println("Total: " + total);
        }
        
        this.totalBiaya = total;
        return total;
       
    }

    // Method to add service cost to the total
    public synchronized void addBiayaJasa(double biaya) {
        System.out.println("Adding service cost: " + biaya);
        this.totalBiaya += biaya;
        System.out.println("Current total service cost: " + this.totalBiaya);
    }

    // Method to get the current total service cost
    public synchronized int getTotalBiayaJasa() {
        calculateTotalBiaya();
        return this.totalBiaya;
    }

    // Optional: Reset the total cost if needed
    public synchronized void resetTotalBiayaJasa() {
        this.totalBiaya = 0;
    }
}
