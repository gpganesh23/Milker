package com.example.trail.Service;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.trail.Repository.UserRepository;
import com.example.trail.Tables.User;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // This is where Spring Security fetches the user info from the IdP's UserInfo endpoint
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Extract user attributes
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String subId = (String) attributes.get("sub"); // The unique subject ID from IdP
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String givenName = (String) attributes.get("given_name");
        String familyName = (String) attributes.get("family_name");
        String pictureUrl = (String) attributes.get("picture");
        Boolean emailVerified = (Boolean) attributes.get("email_verified");

        // --- Database Logic: Save/Update User ---
        Optional<User> existingUser = userRepository.findById(subId); // Use subId as primary key

        User user;
        if (existingUser.isPresent()) {
            user = existingUser.get();
            // Update existing user details (if they might have changed at the IdP)
            user.setEmail(email);
            user.setName(name);
            user.setGivenName(givenName);
            user.setFamilyName(familyName);
            user.setPictureUrl(pictureUrl);
            user.setEmailVerified(emailVerified != null ? emailVerified : false); // Handle null
        } else {
            // New user, create a new record
            user = new User();
            user.setSubId(subId);
            user.setEmail(email);
            user.setName(name);
            user.setGivenName(givenName);
            user.setFamilyName(familyName);
            user.setPictureUrl(pictureUrl);
            user.setEmailVerified(emailVerified != null ? emailVerified : false);
        }

        userRepository.save(user); // Save or update the user in your database

        // Return the OAuth2User object. Spring Security uses this for authentication context.
        return oAuth2User;
    }
}