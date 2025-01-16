package com.example.authservice.service;

import com.example.authservice.dto.UserLoginRequest;
import com.example.authservice.dto.UserRegistrationRequest;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KeycloakService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public KeycloakService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public void createUser(UserRegistrationRequest request) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getPassword());
        credential.setTemporary(false);

        keycloak.realm(realm).users().create(user);
    }

    public String login(UserLoginRequest request) {
        try {
            Keycloak userKeycloak = Keycloak.getInstance(
                    keycloak.getServerInfo().getInfo().get("serverUrl").toString(),
                    realm,
                    request.getUsername(),
                    request.getPassword(),
                    "auth-client"
            );
            return userKeycloak.tokenManager().getAccessTokenString();
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed", e);
        }
    }
}