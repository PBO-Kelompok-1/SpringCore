package com.tubes.pbo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import com.tubes.pbo.models.User;
import com.tubes.pbo.utils.TotalBiayaJasaTracker;
import com.tubes.pbo.utils.TotalBiayaSparepart;

@Controller
public class HomeController {

    //buat total biaya di dashboard admin
     @Autowired
    private TotalBiayaJasaTracker totalBiayaJasaTracker;

    @Autowired
    private TotalBiayaSparepart totalBiayaSparepart;

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
                return "redirect:/dashboard-mekanik";
            }
        } 
        return "index";
    }

    @GetMapping("/login")
    public String login(Principal principal,User user) {
        return "login"; // Jika belum login, tampilkan halaman login
    }
    

    @GetMapping("/index")
    public String index() {
        return "index";
    }


    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        int totalBiayaJasa = totalBiayaJasaTracker.getTotalBiayaJasa();
        System.out.println("Total Biaya Jasa: " + totalBiayaJasa); //debug

        // Menambahkan nilai totalBiayaSparepart ke dalam model
        int totalBiayaSparepartValue = totalBiayaSparepart.getTotalBiayaSparepart();

        int totalPendapatan = totalBiayaJasa + totalBiayaSparepartValue;
        model.addAttribute("totalBiayaJasa", totalBiayaJasa);
        model.addAttribute("totalBiayaSparepart", totalBiayaSparepartValue);
        model.addAttribute("totalPendapatan", totalPendapatan);
        
        return "admin/dashboard";
    }
    
    @GetMapping("/transaksi")
    public String transaksi() {
        return "transaksi";
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

    @GetMapping("/admin/sparepart-admin")
    public String adminSparepartPage() {
        return "admin/sparepart-admin"; 
    }

    @Controller
    public class ErrorController {
    
        @GetMapping("/access-denied")
        public String accessDenied() {
            return "error/access-denied"; // Path sesuai dengan lokasi file HTML
        }
    }
}