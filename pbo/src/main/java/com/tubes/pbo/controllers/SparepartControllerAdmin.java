package com.tubes.pbo.controllers;

import com.tubes.pbo.models.Sparepart;
import com.tubes.pbo.repositories.SparepartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/sparepart-admin")
public class SparepartControllerAdmin {
    @Autowired
    private SparepartRepository sparepartRepository;
    // Get all sparepart
    @GetMapping 
    public String getAllSparepartsAdmin(Model model) {
        List<Sparepart> spareparts = sparepartRepository.findAll();

        model.addAttribute("spareparts", spareparts);       
        return "sparepart-admin";
    }

}
