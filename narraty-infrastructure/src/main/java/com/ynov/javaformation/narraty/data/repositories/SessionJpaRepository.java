package com.ynov.javaformation.narraty.data.repositories;

import com.ynov.javaformation.narraty.data.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface SessionJpaRepository extends JpaRepository<SessionEntity, UUID> {

    Optional<SessionEntity> findByToken(UUID token);

    void deleteAllByUserId(UUID userId);

    @Modifying
    @Query("DELETE FROM SessionEntity s WHERE s.createdAt < :threshold")
    void deleteExpiredSessions(@Param("threshold") LocalDateTime threshold);

}
