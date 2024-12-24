package com.tubes.pbo.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationHandler implements AuthenticationSuccessHandler {

    @Override
    // Dijalanin kalo berhasil login 
    //buat redirect sesuai role
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Cek role user dari Authentication object
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            // Redirect ke dashboard admin
            response.sendRedirect("/dashboard");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEKANIK"))) {
            // Redirect ke dashboard mekanik
            response.sendRedirect("/dashboardmekanik");
        } else {
            // Default redirect (jika role tidak dikenal)
            response.sendRedirect("/");
        }
    }
}
