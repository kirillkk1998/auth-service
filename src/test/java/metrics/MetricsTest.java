package com.example.authservice.metrics;

import com.example.authservice.config.MetricsConfig;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetricsTest {

    private MeterRegistry registry;
    private MetricsConfig metricsConfig;

    @BeforeEach
    void setUp() {
        registry = new SimpleMeterRegistry();
        metricsConfig = new MetricsConfig(registry);
    }

    @Test
    void shouldIncrementLoginAttempts() {
        // Act
        metricsConfig.getLoginAttemptCounter().increment();

        // Assert
        assertEquals(1, registry.get("auth.login.attempts").counter().count());
    }

    @Test
    void shouldIncrementLoginSuccess() {
        // Act
        metricsConfig.getLoginSuccessCounter().increment();

        // Assert
        assertEquals(1, registry.get("auth.login.success").counter().count());
    }

    @Test
    void shouldIncrementLoginFailure() {
        // Act
        metricsConfig.getLoginFailureCounter().increment();

        // Assert
        assertEquals(1, registry.get("auth.login.failures").counter().count());
    }

    @Test
    void shouldIncrementRegistration() {
        // Act
        metricsConfig.getRegistrationCounter().increment();

        // Assert
        assertEquals(1, registry.get("auth.registration").counter().count());
    }
}