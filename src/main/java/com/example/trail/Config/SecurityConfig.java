package com.example.trail.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
// No need for AuthenticationSuccessHandler or SimpleUrlAuthenticationSuccessHandler imports if using defaultSuccessUrl
import static org.springframework.security.config.Customizer.withDefaults;

import com.example.trail.Service.CustomOAuth2UserService; // Assuming this path is correct

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
                // You can add paths to ignore CSRF if needed, e.g., for specific public POSTs
                // .ignoringRequestMatchers("/some/public/post/endpoint")
            )
           
                .authorizeHttpRequests(authorize -> authorize
                	    .requestMatchers(
                	        "/", "/index.html", "/reviews.html",
                	        "/css/**", "/js/**", "/img/**",
                	        "/error",
                	        "/api/csrf-token" // <--- ADD THIS LINE
                	    ).permitAll()
                	    // ... rest of your configuration
                	

                // Allow GET requests to /api/comments for anyone to view comments
                .requestMatchers(HttpMethod.GET, "/api/comments").permitAll()

                // Allow /api/user/current (or /api/comments/user/current if you stick to that path)
                // to check authentication status without forcing a redirect for unauthenticated users.
                // This is crucial for your frontend's isAuthenticated() function.
                // NOTE: If you changed UserController to /api/user/current, ensure that path is here.
                .requestMatchers("/api/user/current").permitAll() // Assuming you fixed UserController to /api/user/current
                .requestMatchers("/api/checkout/placeOrder").permitAll()
                // All other requests (e.g., POST /api/comments) require authentication.
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2Login -> oauth2Login
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService) // Use your custom user service
                )
                // --- CRITICAL CHANGE HERE ---
                // Redirect to the root ("/") after successful OAuth2 login.
                // The 'true' argument ensures it's always a redirect, even if the user was originally
                // trying to access a protected page. This helps ensure CSRF token is set.
                .defaultSuccessUrl("/", true) // <--- CHANGED THIS LINE
                // You can specify "/reviews.html" if you want to go directly there after login
                // .defaultSuccessUrl("/reviews.html", true)
            )
            // Removed .formLogin(withDefaults()); as it's typically not needed with OAuth2Login
            // unless you also support traditional form-based login.
            .logout(logout -> logout
                .logoutSuccessUrl("/index.html") // Redirect to index after logout
                .permitAll() // Ensure logout endpoint is accessible
            );

        return http.build();
    }

    // --- REMOVED THIS BEAN ---
    // public AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler() {
    //     return new SimpleUrlAuthenticationSuccessHandler("/");
    // }
}