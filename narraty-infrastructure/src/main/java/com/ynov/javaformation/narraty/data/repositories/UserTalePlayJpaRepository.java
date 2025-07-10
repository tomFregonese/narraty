package com.ynov.javaformation.narraty.data.repositories;

import com.ynov.javaformation.narraty.data.entities.UserTalePlayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserTalePlayJpaRepository extends JpaRepository<UserTalePlayEntity, UUID> {

    Optional<UserTalePlayEntity> findByUserIdAndTaleId(UUID userId, UUID taleId);

}
