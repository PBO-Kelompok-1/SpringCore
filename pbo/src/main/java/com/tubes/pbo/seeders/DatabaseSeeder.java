package com.tubes.pbo.seeders;

import com.tubes.pbo.models.User;
import com.tubes.pbo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.tubes.pbo.models.Pelanggan;
import com.tubes.pbo.repositories.PelangganRepository;




@Component
public class DatabaseSeeder implements CommandLineRunner {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private PelangganRepository pelangganRepository;



  @Override
  public void run(String... args) throws Exception {
    if (userRepository.findByUsername("admin").isEmpty()) {
      User admin = new User();
      admin.setUsername("admin");
      admin.setPassword(passwordEncoder.encode("admin123"));
      admin.setRole("ADMIN");
      userRepository.save(admin);
      System.out.println("Admin user created");
    } else {
      System.out.println("Admin user already exists");
    }

    //tes bikin mekanik
    if (userRepository.findByUsername("mekanik1").isEmpty()) {
      User mekanik1 = new User();
      mekanik1.setUsername("mekanik1");
      mekanik1.setPassword(passwordEncoder.encode("mekanik123"));
      mekanik1.setRole("MEKANIK");
      userRepository.save(mekanik1);
      System.out.println("mekanik1 user created");
    } else {
      System.out.println("mekanik1 user already exists");
    }

    if (pelangganRepository.findByNama("John Doe").isEmpty()) {
      Pelanggan pelanggan1 = new Pelanggan();
      pelanggan1.setNama("John Doe");
      pelanggan1.setAlamat("Jl. Merdeka No. 1");
      pelanggan1.setNo_hp("08123456789");
      pelangganRepository.save(pelanggan1);
      System.out.println("Pelanggan John Doe created");
    } else {
      System.out.println("Pelanggan John Doe already exists");
    }
    
    if (pelangganRepository.findByNama("Jane Smith").isEmpty()) {
      Pelanggan pelanggan2 = new Pelanggan();
      pelanggan2.setNama("Jane Smith");
      pelanggan2.setAlamat("Jl. Sudirman No. 2");
      pelanggan2.setNo_hp("08987654321");
      pelangganRepository.save(pelanggan2);
      System.out.println("Pelanggan Jane Smith created");
    } else {
      System.out.println("Pelanggan Jane Smith already exists");
    }
  }

  
}
