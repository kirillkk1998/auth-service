package com.example.authservice.integration;

import com.example.authservice.dto.UserLoginRequest;
import com.example.authservice.dto.UserRegistrationRequest;
import com.example.authservice.service.KeycloakService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
public class KeycloakIntegrationTest {

    @Container
    static GenericContainer<?> keycloak = new GenericContainer<>("quay.io/keycloak/keycloak:latest")
            .withExposedPorts(8080)
            .withEnv("KEYCLOAK_ADMIN", "admin")
            .withEnv("KEYCLOAK_ADMIN_PASSWORD", "admin")
            .withCommand("start-dev");

    @Autowired
    private KeycloakService keycloakService;

    @DynamicPropertySource
    static void registerKeycloakProperties(DynamicPropertyRegistry registry) {
        registry.add("keycloak.auth-server-url",
                () -> String.format("http://localhost:%d", keycloak.getMappedPort(8080)));
    }

    @Test
    void shouldRegisterAndLoginUser() {
        // Arrange
        UserRegistrationRequest registrationRequest = new UserRegistrationRequest();
        registrationRequest.setUsername("testuser");
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPassword("password123");

        // Act & Assert - Registration
        assertDoesNotThrow(() -> keycloakService.createUser(registrationRequest));

        // Act & Assert - Login
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");

        String token = keycloakService.login(loginRequest);
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }
}
