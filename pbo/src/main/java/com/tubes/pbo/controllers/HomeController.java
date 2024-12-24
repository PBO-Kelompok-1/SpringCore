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
       
        return "login";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }


    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard"; //ke lokasi /admin/dashboard
    }

    @GetMapping("/transaksi")
    public String transaksi() {
        return "transaksi";
    }

    @GetMapping("/ tesmekanik")
    public String  tesmekanik() {
        return " tesmekanik";
    }
   
    @Controller
    public class ErrorController {
    
        @GetMapping("/access-denied")
        public String accessDenied() {
            return "error/access-denied"; // Path sesuai dengan lokasi file HTML
        }
    }



}