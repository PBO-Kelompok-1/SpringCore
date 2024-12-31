package com.tubes.pbo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tubes.pbo.models.Sparepart;
import com.tubes.pbo.repositories.SparepartRepository;

@Controller
@RequestMapping("/sparepart")
public class SparepartController {

    @Autowired
    private SparepartRepository sparepartRepository;

    @PostMapping
    public String addSparepart(@ModelAttribute Sparepart sparepart, Authentication authentication) {
        sparepartRepository.save(sparepart);

        // Cek role pengguna setelah menambahkan sparepart (biar ttp stay di page sesuai role)
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            // Jika role adalah ADMIN, arahkan ke halaman sparepart-admin
            if (user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/sparepart-admin";  // Redirect ke sparepart-admin untuk admin
            }
        }

        // Jika bukan admin, arahkan ke halaman sparepart biasa
        return "redirect:/sparepart";  // Redirect ke sparepart untuk mekanik
    }

    // Get all sparepart
    @GetMapping
    public String getAllSpareparts(Model model) {
        List<Sparepart> spareparts = sparepartRepository.findAll();

        model.addAttribute("spareparts", spareparts);

        return "sparepart";
    }

    // Get sparepart by ID
    @GetMapping("/{id}")
    public ResponseEntity<Sparepart> getSparepartById(@PathVariable int id) {
        Optional<Sparepart> sparepart = sparepartRepository.findById(id);
        return sparepart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update sparepart (using form data)
    @PutMapping("/{id}")
    public String updateSparepart(@PathVariable int id, @ModelAttribute Sparepart updatedSparepart, Authentication authentication) {

        
        Optional<Sparepart> existingSparepart = sparepartRepository.findById(id);
        if (existingSparepart.isPresent()) {
            Sparepart sparepart = existingSparepart.get();
            sparepart.setNama(updatedSparepart.getNama());
            sparepart.setDeskripsi(updatedSparepart.getDeskripsi());
            sparepart.setHarga(updatedSparepart.getHarga());
            sparepart.setStok(updatedSparepart.getStok());
            sparepartRepository.save(sparepart);
        }

        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            // Jika role adalah ADMIN, arahkan ke halaman sparepart-admin
            if (user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/sparepart-admin";  // Redirect ke sparepart-admin untuk admin
            }
        }

        return "redirect:/sparepart";
    }

    @DeleteMapping("/{id}")
    public String deleteSparepart(@PathVariable int id) {
        if (sparepartRepository.existsById(id)) {
            sparepartRepository.deleteById(id);
        }
        return "redirect:/sparepart";
    }
}


