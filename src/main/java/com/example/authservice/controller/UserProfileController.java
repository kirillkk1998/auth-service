package com.example.authservice.controller;

import com.example.authservice.dto.UserProfileUpdateRequest;
import com.example.authservice.service.UserProfileService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/profile")
    @Timed(value = "get.user.profile", description = "Time taken to get user profile")
    @Operation(summary = "Get user profile", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<UserRepresentation> getUserProfile(Authentication authentication) {
        String userId = authentication.getName();
        return ResponseEntity.ok(userProfileService.getUserProfile(userId));
    }

    @PutMapping("/profile")
    @Timed(value = "update.user.profile", description = "Time taken to update user profile")
    @Operation(summary = "Update user profile", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<Void> updateUserProfile(
            Authentication authentication,
            @RequestBody UserProfileUpdateRequest request) {
        String userId = authentication.getName();
        userProfileService.updateUserProfile(userId, request);
        return ResponseEntity.ok().build();
    }
}