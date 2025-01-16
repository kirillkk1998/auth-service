package com.example.authservice.controller;

import com.example.authservice.dto.UserLoginRequest;
import com.example.authservice.dto.UserRegistrationRequest;
import com.example.authservice.service.KeycloakService;
import com.example.authservice.config.MetricsConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "API для аутентификации пользователей")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final KeycloakService keycloakService;
    private final MetricsConfig metricsConfig;

    public AuthController(KeycloakService keycloakService, MetricsConfig metricsConfig) {
        this.keycloakService = keycloakService;
        this.metricsConfig = metricsConfig;
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован")
    @ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
    @Timed(value = "auth.registration.time", description = "Time taken to register user")
    public ResponseEntity<Void> register(@Valid @RequestBody UserRegistrationRequest request) {
        logger.info("Attempting to register user: {}", request.getUsername());
        metricsConfig.getRegistrationCounter().increment();

        keycloakService.createUser(request);

        logger.info("Successfully registered user: {}", request.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @Operation(summary = "Аутентификация пользователя")
    @ApiResponse(responseCode = "200", description = "Успешная аутентификация")
    @ApiResponse(responseCode = "401", description = "Неверные учетные данные")
    @Timed(value = "auth.login.time", description = "Time taken to login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginRequest request) {
        logger.info("Attempting to authenticate user: {}", request.getUsername());
        metricsConfig.getLoginAttemptCounter().increment();

        try {
            String token = keycloakService.login(request);
            logger.info("Successfully authenticated user: {}", request.getUsername());
            metricsConfig.getLoginSuccessCounter().increment();
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            logger.error("Failed to authenticate user: {}", request.getUsername(), e);
            metricsConfig.getLoginFailureCounter().increment();
            throw e;
        }
    }
}
