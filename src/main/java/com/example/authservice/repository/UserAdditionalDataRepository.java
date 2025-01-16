package com.example.authservice.repository;

import com.example.authservice.model.UserAdditionalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAdditionalDataRepository extends JpaRepository<UserAdditionalData, String> {
    Optional<UserAdditionalData> findByKeycloakUserId(String keycloakUserId);
}
