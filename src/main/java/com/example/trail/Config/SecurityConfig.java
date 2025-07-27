package com.example.trail.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import com.example.trail.Service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    "/", "/index.html", "/reviews.html", "/cart.html", // <-- ADDED /cart.html
                    "/css/**", "/js/**", "/img/**",
                    "/error",
                    "/api/csrf-token"
                ).permitAll()

                // --- ADD THESE LINES FOR PRODUCT API ACCESS ---
                // Assuming your product API is at /api/products or /products
                // If products should be visible to *everyone* (unauthenticated users):
                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/products/**").permitAll() // If you have a top-level /products endpoint

                // Existing rules:
                .requestMatchers(HttpMethod.GET, "/api/comments").permitAll()
                .requestMatchers("/api/user/current").permitAll()
                .requestMatchers("/api/checkout/placeOrder").permitAll() // Consider if this should be authenticated
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2Login -> oauth2Login
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService)
                )
                .defaultSuccessUrl("/", true)
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/index.html")
                .permitAll()
            );

        return http.build();
    }
}