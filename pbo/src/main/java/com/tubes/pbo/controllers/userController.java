package com.tubes.pbo.controllers;

import com.tubes.pbo.models.User;
import com.tubes.pbo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Menambahkan data user (Create)
    @PostMapping("/add")
    public String addUser(@RequestBody User newUser) {
        userRepository.save(newUser);
        return "User berhasil ditambahkan!";
    }

    // Mengambil semua data user (Read)
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Mengambil data user berdasarkan ID (Read)
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable int id) {
        return Optional.ofNullable(userRepository.findById(id));
    }

    // Mengambil data user berdasarkan username (Read)
    @GetMapping("/username/{username}")
    public Optional<User> getUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }

    // Menghapus user berdasarkan ID (Delete)
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
        return "User dengan ID " + id + " berhasil dihapus!";
    }
}
