package com.example.trail.Controller;



import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class CsrfTokenController {

    @GetMapping("/api/csrf-token")
    public String getCsrfToken(HttpServletRequest request, HttpServletResponse response) {
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrf != null) {
            response.setHeader(csrf.getHeaderName(), csrf.getToken());
            return csrf.getToken(); // You can return it in the body for easy access, or just rely on the header
        }
        return "CSRF token not available"; // Should ideally not happen if CSRF is enabled
    }
}
