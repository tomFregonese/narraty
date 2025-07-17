package com.ynov.javaformation.narraty.data.repositories;

import com.ynov.javaformation.narraty.data.entities.SceneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface SceneJpaRepository extends JpaRepository<SceneEntity, UUID> {
    Collection<SceneEntity> findAllByTaleId(UUID taleId);
}
