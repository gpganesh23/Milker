package com.example.trail.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user") // <--- CHANGE THIS LINE BACK TO "/api/user"
public class UserController {

    @GetMapping("/current") // This will now correctly make the full path /api/user/current
    public ResponseEntity<Map<String, Object>> getCurrentUser(@AuthenticationPrincipal OAuth2User oauth2User) {
        if (oauth2User == null) {
            // User is not authenticated
            return ResponseEntity.ok(Collections.singletonMap("isAuthenticated", false));
        }

        // User is authenticated, return relevant attributes
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("isAuthenticated", true);
        userDetails.put("name", oauth2User.getAttribute("name"));
        userDetails.put("email", oauth2User.getAttribute("email"));
        userDetails.put("subId", oauth2User.getAttribute("sub"));
        userDetails.put("picture", oauth2User.getAttribute("picture"));
        userDetails.put("emailVerified", oauth2User.getAttribute("email_verified"));

        return ResponseEntity.ok(userDetails);
    }

    // If you have comments-related endpoints, they should be in a separate CommentsController
    // or under a different @RequestMapping in this controller, like:
    // @GetMapping("/comments/latest") or @PostMapping("/comments/submit")
}