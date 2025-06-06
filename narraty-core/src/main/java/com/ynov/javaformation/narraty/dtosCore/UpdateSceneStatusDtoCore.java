package com.ynov.javaformation.narraty.dtosCore;

import com.ynov.javaformation.narraty.models.SceneStatus;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UpdateSceneStatusDtoCore {

    public UUID sceneId;

    public SceneStatus status;

}
