package com.ynov.javaformation.narraty.models;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@Builder
public class Scene {

    public UUID id;

    public String title;

    public String text;

    public UUID taleId;

    public SceneStatus status;

    /**
     * If the scene has one choice then it is a simple reading page.
     * If the scene has multiple choices then it is a choice page.
     * If the scene has no choices then it is an ending page.
     */
    public Collection<Choice> choices;

}
