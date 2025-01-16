package com.example.authservice.service;

import com.example.authservice.dto.UserRegistrationRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.keycloak.admin.client.Keycloak;

@ExtendWith(MockitoExtension.class)
public class KeycloakServiceTest {
    @Mock
    private Keycloak keycloak;

    @InjectMocks
    private KeycloakService keycloakService;

    @Test
    void createUser_Failure() {
        // Arrange
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password");

        // Act & Assert
        assertThrows(RuntimeException.class, () -> keycloakService.createUser(request));
    }
}