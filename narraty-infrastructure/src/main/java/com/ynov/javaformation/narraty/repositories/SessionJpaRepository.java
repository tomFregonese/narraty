package com.ynov.javaformation.narraty.repositories;

import com.ynov.javaformation.narraty.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SessionJpaRepository extends JpaRepository<SessionEntity, UUID> {

    Optional<SessionEntity> findByToken(UUID token);

    void deleteAllByUserId(UUID userId);

}
