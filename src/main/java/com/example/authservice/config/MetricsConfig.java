package com.example.authservice.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {
    private final Counter loginAttemptCounter;
    private final Counter loginSuccessCounter;
    private final Counter loginFailureCounter;
    private final Counter registrationCounter;

    public MetricsConfig(MeterRegistry registry) {
        this.loginAttemptCounter = Counter.builder("auth.login.attempts")
                .description("Number of login attempts")
                .register(registry);

        this.loginSuccessCounter = Counter.builder("auth.login.success")
                .description("Number of successful logins")
                .register(registry);

        this.loginFailureCounter = Counter.builder("auth.login.failures")
                .description("Number of failed logins")
                .register(registry);

        this.registrationCounter = Counter.builder("auth.registration")
                .description("Number of user registrations")
                .register(registry);
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    public Counter getLoginAttemptCounter() {
        return loginAttemptCounter;
    }

    public Counter getLoginSuccessCounter() {
        return loginSuccessCounter;
    }

    public Counter getLoginFailureCounter() {
        return loginFailureCounter;
    }

    public Counter getRegistrationCounter() {
        return registrationCounter;
    }
}