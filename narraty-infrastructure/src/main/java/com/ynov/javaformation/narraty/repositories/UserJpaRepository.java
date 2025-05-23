package com.ynov.javaformation.narraty.repositories;

import com.ynov.javaformation.narraty.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> { }
