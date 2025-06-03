package com.ynov.javaformation.narraty.models;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Builder
public class User {

    public UUID id;

    public String username;

    public String email;

    public String passwordHash;

    public int experiencePoints;

    public LocalDateTime createdAt;

    public LocalDateTime updatedAt;

}
