package com.tubes.pbo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {


    @GetMapping("/")
    public String home(Principal principal, Model model) {
        if (principal != null) {
            // ... (logika untuk user yang sudah login)
            String username = principal.getName();
            model.addAttribute("username", username);

            if ("ADMIN".equals(username)) {
                return "redirect:/admin";
            } else if ("MEKANIK".equals(username)) {
                return "redirect:/mekanik";
            }
        } 
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("errorMessage", "Login Gagal. Periksa kembali username dan password Anda.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Anda telah berhasil logout.");
        }
        return "login";
    }
    
    @GetMapping("/index")
    public String index() {
        return "index";
    }


    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }
}