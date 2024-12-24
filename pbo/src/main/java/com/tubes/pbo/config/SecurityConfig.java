package com.tubes.pbo.config;

import com.tubes.pbo.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Nonaktifkan CSRF jika tidak diperlukan
            .authorizeHttpRequests(authorize -> 
                authorize
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/mekanik/**").hasRole("MEKANIK")
                    .requestMatchers("/transaksi/**").hasRole("ADMIN")
                    .requestMatchers("/tesmekanik/**").hasRole("MEKANIK")
                    .requestMatchers("/", "/index", "/login").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(formLogin -> 
                formLogin
                    .loginPage("/login")
                    .defaultSuccessUrl("/dashboard", true)
                    .failureUrl("/login?error=true")
                    .permitAll()
            )
            .logout(logout -> 
                logout
                    .logoutUrl("/logout")
                    .invalidateHttpSession(true) ///hapus session
                    .deleteCookies("JSESSIONID") // hapus cookie
                    .logoutSuccessUrl("/login")
                    .permitAll() 
            )
            .sessionManagement(session -> 
                session
                    .maximumSessions(1) 
                    .maxSessionsPreventsLogin(false) 
                    .and() 
                    .sessionFixation().migrateSession() //
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) 
                    .invalidSessionUrl("/login?expired=true") // masih timeout default 1800 detik gatau set dimana 
            )
                .exceptionHandling(exceptionHandling -> 
                   exceptionHandling
              .accessDeniedPage("/access-denied") // halaman jika akses ditolak
          );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
}
