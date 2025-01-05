package com.tubes.pbo.seeders;

import com.tubes.pbo.models.Transaksi;
import com.tubes.pbo.models.Pelanggan;
import com.tubes.pbo.models.User;

import com.tubes.pbo.repositories.TransaksiRepository;
import com.tubes.pbo.repositories.PelangganRepository;
import com.tubes.pbo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TransaksiSeeder implements CommandLineRunner {

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private PelangganRepository pelangganRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {        
        if (transaksiRepository.findById(1L).isEmpty()) {
            Transaksi transaksi1 = new Transaksi();
            transaksi1.setCatatan("Servis rutin");
            transaksi1.setStnk("1234567890");
            transaksi1.setMotor("Honda Beat");
            transaksi1.setBiayaJasa(150000);
            transaksi1.setStatus("on going");
            transaksi1.setTanggalTransaksi(new Date()); 


            Pelanggan pelanggan1 = pelangganRepository.findById(1).orElse(null);
            User mekanik1 = userRepository.findById(2);


            transaksi1.setPelanggan(pelanggan1);
            transaksi1.setMekanik(mekanik1);


            transaksiRepository.save(transaksi1);
            System.out.println("Transaksi 1 created");
        } else {
            System.out.println("Transaksi 1 already exists");
        }

        
        if (transaksiRepository.findById(2L).isEmpty()) {
            Transaksi transaksi2 = new Transaksi();
            transaksi2.setCatatan("Ganti oli");
            transaksi2.setStnk("0987654321");
            transaksi2.setMotor("Yamaha NMAX");
            transaksi2.setBiayaJasa(100000);
            transaksi2.setStatus("on going");
            transaksi2.setTanggalTransaksi(new Date());
            Pelanggan pelanggan2 = pelangganRepository.findById(2).orElse(null);
            User mekanik2 = userRepository.findById(2);

            transaksi2.setPelanggan(pelanggan2);
            transaksi2.setMekanik(mekanik2);

            transaksiRepository.save(transaksi2);
            System.out.println("Transaksi 2 created");
        } else {
            System.out.println("Transaksi 2 already exists");
        }


        if (transaksiRepository.findById(3L).isEmpty()) {
            Transaksi transaksi3 = new Transaksi();
            transaksi3.setCatatan("Ganti kampas rem");
            transaksi3.setStnk("1122334455");
            transaksi3.setMotor("Suzuki Satria");
            transaksi3.setBiayaJasa(75000);
            transaksi3.setStatus("on going");
            transaksi3.setTanggalTransaksi(new Date());

            Pelanggan pelanggan3 = pelangganRepository.findById(1).orElse(null);
            User mekanik3 = userRepository.findById(2);

            transaksi3.setPelanggan(pelanggan3);
            transaksi3.setMekanik(mekanik3);

            transaksiRepository.save(transaksi3);
            System.out.println("Transaksi 3 created");
        } else {
            System.out.println("Transaksi 3 already exists");
        }
        
    }

}