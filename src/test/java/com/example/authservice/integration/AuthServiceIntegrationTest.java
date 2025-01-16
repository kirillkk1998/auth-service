package com.example.authservice.integration;

import com.example.authservice.dto.UserRegistrationRequest;
import com.example.authservice.service.KeycloakService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test")
public class AuthServiceIntegrationTest {

    @Autowired
    private KeycloakService keycloakService;

    @Test
    void fullRegistrationFlow() {
        // Arrange
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("integrationtest");
        request.setEmail("integration@test.com");
        request.setPassword("password123");

        // Act & Assert
        assertDoesNotThrow(() -> keycloakService.createUser(request));
    }
}