package com.tubes.pbo.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tubes.pbo.repositories.TransaksiRepository;
import com.tubes.pbo.models.Transaksi;

@Service
public class TransactionService {

    @Autowired
    private TransaksiRepository transactionRepository;

    // Memperbaiki metode pencarian berdasarkan nama pelanggan
    public List<Transaksi> findByCustomerName(String customerName) {
        return transactionRepository.findByPelanggan_NamaContainingIgnoreCase(customerName);
    }

    public List<Transaksi> findAll() {
        return transactionRepository.findAll();
    }

    
}