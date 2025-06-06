package com.ynov.javaformation.narraty.models;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.UUID;

@AllArgsConstructor
@Builder
public class Choice {

    public UUID id;

    public String text;

    public UUID sceneId;

    public UUID nextSceneId;

}
