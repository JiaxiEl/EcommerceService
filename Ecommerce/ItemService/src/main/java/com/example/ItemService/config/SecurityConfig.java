package com.example.ItemService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Use this line to disable CSRF in the updated way.
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Adjust as needed to secure your routes
                );

        return http.build();
    }
}
