package com.tubes.pbo.config;

import com.tubes.pbo.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(authorizeHttpRequests ->
        authorizeHttpRequests
          .requestMatchers("/admin/**").hasRole("ADMIN")
          .requestMatchers("/mekanik/**").hasRole("MEKANIK")
          .requestMatchers("/", "/index", "/signup", "/login", "/css/**", "/js/**", "/images/**").permitAll()
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
              .permitAll()
              .logoutSuccessUrl("/login?logout=true")
      );

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
