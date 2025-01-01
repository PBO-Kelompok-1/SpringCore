package com.tubes.pbo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TotalPendapatan {

    @Autowired
    private TotalBiayaJasaTracker totalBiayaJasaTracker;

    @Autowired
    private TotalBiayaSparepart totalBiayaSparepart;

    private int totalPendapatan;

    public void calculateTotalPendapatan() {
        // Memastikan biaya jasa dan sparepart dihitung
        int totalBiayaJasa = totalBiayaJasaTracker.getTotalBiayaJasa();
        int totalBiayaSparepartValue = totalBiayaSparepart.getTotalBiayaSparepart(); // Mengambil total biaya sparepart

        // Menambahkan kedua total biaya
        this.totalPendapatan = totalBiayaJasa + totalBiayaSparepartValue;
    }

    public int getTotalPendapatan() {
        return totalPendapatan;
    }
}
