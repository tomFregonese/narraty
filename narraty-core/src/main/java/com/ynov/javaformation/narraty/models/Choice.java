package com.ynov.javaformation.narraty.models;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class Choice {

    public UUID id;

    public String text;

    public UUID sceneId;

    public UUID nextSceneId;

}
