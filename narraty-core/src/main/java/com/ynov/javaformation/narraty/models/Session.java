package com.ynov.javaformation.narraty.models;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
public class Session {

    public UUID token;

    public UUID userId;

    public LocalDateTime createdAt;

}
