package com.tubes.pbo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import com.tubes.pbo.models.User;

@Controller
public class HomeController {


    @GetMapping("/")
    public String home(Principal principal, Model model,User user) {
        if (principal != null) {
            
            //sebenernya ini ga perlu karena udah ada di security config
            // ... (logika untuk user yang sudah login)
            String username = principal.getName();
            String role = user.getRole();
           // String role = principal.getAuthorities().iterator().next().getAuthority();
            model.addAttribute("username", username);

            if ("ADMIN".equals(role)) {
                return "redirect:/dashboard";
            } else if ("MEKANIK".equals(role)) {
                return "redirect:/dashboardmekanik";
            }
        } 
        return "index";
    }

    @GetMapping("/login")
    public String login(Principal principal,User user) {

        //user getrole gak fungsi
        String role = user.getRole();
        if (principal != null) {
            // Redirect sesuai role jika sudah login
            if ("ADMIN".equals(role)) {
                return "redirect:/dashboard";
            } else if ("MEKANIK".equals(role)) {
                return "redirect:/dashboardmekanik";
            }
        }
        return "login"; // Jika belum login, tampilkan halaman login
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

    @GetMapping("/dashboardmekanik")
    public String  dashboardmekanik() {
        return "dashboardmekanik";
    }
   
    @GetMapping("/admin/users")
    public String adminUsersPage() {
        return "admin/users"; // Path ke templates/admin/users.html
    }

    @GetMapping("/admin/mechanics")
    public String adminMechanicsPage() {
        return "admin/mekanik"; 
    }

    @GetMapping("/admin/pelanggan")
    public String adminPelangganPage() {
        return "admin/pelanggan"; 
    }

    @Controller
    public class ErrorController {
    
        @GetMapping("/access-denied")
        public String accessDenied() {
            return "error/access-denied"; // Path sesuai dengan lokasi file HTML
        }
    }



}