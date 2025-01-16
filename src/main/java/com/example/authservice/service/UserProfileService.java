package com.example.authservice.service;

import com.example.authservice.dto.UserProfileUpdateRequest;
import com.example.authservice.exception.AuthException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public UserProfileService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public UserRepresentation getUserProfile(String userId) {
        try {
            return keycloak.realm(realm).users().get(userId).toRepresentation();
        } catch (Exception e) {
            throw new AuthException("Failed to get user profile", e);
        }
    }

    public void updateUserProfile(String userId, UserProfileUpdateRequest request) {
        try {
            UserRepresentation user = keycloak.realm(realm).users().get(userId).toRepresentation();
            user.setEmail(request.getEmail());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());

            keycloak.realm(realm).users().get(userId).update(user);
        } catch (Exception e) {
            throw new AuthException("Failed to update user profile", e);
        }
    }
}