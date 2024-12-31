package com.tubes.pbo.seeders;

import com.tubes.pbo.models.Sparepart;
import com.tubes.pbo.repositories.SparepartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SparepartSeeder implements CommandLineRunner {

    @Autowired
    private SparepartRepository sparepartRepository;

    @Override
    public void run(String... args) throws Exception {
        if (sparepartRepository.findByNama("Oli Mesin").isEmpty()) {
            Sparepart oliMesin = new Sparepart();
            oliMesin.setNama("Oli Mesin");
            oliMesin.setHarga(75500.0);
            oliMesin.setStok(20);
            sparepartRepository.save(oliMesin);
            System.out.println("Sparepart Oli Mesin created");
        } else {
            System.out.println("Sparepart Oli Mesin already exists");
        }

        if (sparepartRepository.findByNama("Kampas Rem").isEmpty()) {
            Sparepart kampasRem = new Sparepart();
            kampasRem.setNama("Kampas Rem");
            kampasRem.setHarga(50000.0);
            kampasRem.setStok(25);
            sparepartRepository.save(kampasRem);
            System.out.println("Sparepart Kampas Rem created");
        } else {
            System.out.println("Sparepart Kampas Rem already exists");
        }

        if (sparepartRepository.findByNama("Filter Udara").isEmpty()) {
            Sparepart filterUdara = new Sparepart();
            filterUdara.setNama("Filter Udara");
            filterUdara.setHarga(45000.0);
            filterUdara.setStok(30);
            sparepartRepository.save(filterUdara);
            System.out.println("Sparepart Filter Udara created");
        } else {
            System.out.println("Sparepart Filter Udara already exists");
        }

        if (sparepartRepository.findByNama("Aki").isEmpty()) {
            Sparepart aki = new Sparepart();
            aki.setNama("Aki");
            aki.setHarga(300000.0);
            aki.setStok(10);
            sparepartRepository.save(aki);
            System.out.println("Sparepart Aki created");
        } else {
            System.out.println("Sparepart Aki already exists");
        }

        if (sparepartRepository.findByNama("Busi").isEmpty()) {
            Sparepart busi = new Sparepart();
            busi.setNama("Busi");
            busi.setHarga(15000);
            busi.setStok(200);
            sparepartRepository.save(busi);
            System.out.println("Sparepart 'Busi' created");
        } else {
            System.out.println("Sparepart 'Busi' already exists");
        }
    }
}
