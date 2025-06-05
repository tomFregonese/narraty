package com.ynov.javaformation.narraty.models;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@Builder
public class Tale {

    public UUID id;

    public String title;

    public String description;

    public LocalDateTime createdAt;

    public LocalDateTime updatedAt;

    public UUID authorId;

    public Collection<Scene> scenes;

    public int playCount;

    public TaleStatus status;

}
