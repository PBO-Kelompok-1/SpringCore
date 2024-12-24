package com.tubes.pbo.seeders;

import com.tubes.pbo.models.User;
import com.tubes.pbo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;




@Component
public class DatabaseSeeder implements CommandLineRunner {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

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
  }
}