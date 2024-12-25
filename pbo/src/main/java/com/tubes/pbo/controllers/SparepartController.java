package com.tubes.pbo.controllers;

import com.tubes.pbo.models.Sparepart;
import com.tubes.pbo.repositories.SparepartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sparepart")
public class SparepartController {

    @Autowired
    private SparepartRepository sparepartRepository;

    @PostMapping
    public String addSparepart(@ModelAttribute Sparepart sparepart) {
        sparepartRepository.save(sparepart);

        return "redirect:/sparepart";
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
    public String updateSparepart(@PathVariable int id, @ModelAttribute Sparepart updatedSparepart) {
        Optional<Sparepart> existingSparepart = sparepartRepository.findById(id);
        if (existingSparepart.isPresent()) {
            Sparepart sparepart = existingSparepart.get();
            sparepart.setNama(updatedSparepart.getNama());
            sparepart.setDeskripsi(updatedSparepart.getDeskripsi());
            sparepart.setHarga(updatedSparepart.getHarga());
            sparepart.setStok(updatedSparepart.getStok());
            sparepartRepository.save(sparepart);
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
