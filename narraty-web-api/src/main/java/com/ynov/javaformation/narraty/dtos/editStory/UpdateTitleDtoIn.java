package com.ynov.javaformation.narraty.dtos.editStory;

import com.ynov.javaformation.narraty.dtosCore.UpdateSceneTitleDtoCore;
import com.ynov.javaformation.narraty.dtosCore.UpdateTaleTitleDtoCore;

import java.util.UUID;

public class UpdateTitleDtoIn {

    public String ttl;


    public UpdateTaleTitleDtoCore mapToTaleDomain(UUID taleId) {
        return new UpdateTaleTitleDtoCore(
                taleId,
                this.ttl
        );
    }

    public UpdateSceneTitleDtoCore mapToSceneDomain(UUID sceneId) {
        return new UpdateSceneTitleDtoCore(
                sceneId,
                this.ttl
        );
    }

}
