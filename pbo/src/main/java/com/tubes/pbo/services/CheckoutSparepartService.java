package com.tubes.pbo.services;

import com.tubes.pbo.models.CheckoutSparepart;
import com.tubes.pbo.models.Pelanggan;
import com.tubes.pbo.repositories.CheckoutSparepartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CheckoutSparepartService {

    @Autowired
    private CheckoutSparepartRepository checkoutSparepartRepository;

    public CheckoutSparepart saveCheckoutSparepart(CheckoutSparepart checkoutSparepart) {
        return checkoutSparepartRepository.save(checkoutSparepart);
    }

    public List<CheckoutSparepart> getAllCheckoutSpareparts() {
        return checkoutSparepartRepository.findAll();
    }

    public CheckoutSparepart getCheckoutSparepartById(int id) {
        Optional<CheckoutSparepart> result = checkoutSparepartRepository.findById(id);
        return result.orElse(null);
    }

    public CheckoutSparepart updateCheckoutSparepart(int id, CheckoutSparepart updatedCheckoutSparepart) {
        if (checkoutSparepartRepository.existsById(id)) {
            updatedCheckoutSparepart.setId(id);
            return checkoutSparepartRepository.save(updatedCheckoutSparepart);
        } else {
            return null;
        }
    }

    public boolean deleteCheckoutSparepart(int id) {
        if (checkoutSparepartRepository.existsById(id)) {
            checkoutSparepartRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<CheckoutSparepart> getCheckoutSparepartsByTransaksiId(int transaksiId) {
        return checkoutSparepartRepository.findByTransaksiId(transaksiId);
    }

    public List<CheckoutSparepart> getCheckoutSparepartsBySparepartId(int sparepartId) {
        return checkoutSparepartRepository.findBySparepartId(sparepartId);
    }

    public List<Pelanggan> findPelangganBySparepartNama(String namaSparepart) {
        return checkoutSparepartRepository.findPelangganBySparepartNama(namaSparepart);
    }
}
