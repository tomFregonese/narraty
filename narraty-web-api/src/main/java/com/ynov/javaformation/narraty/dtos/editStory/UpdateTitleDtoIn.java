package com.ynov.javaformation.narraty.dtos.editStory;

import com.ynov.javaformation.narraty.dtosCore.UpdateSceneTitleDtoCore;
import com.ynov.javaformation.narraty.dtosCore.UpdateTaleTitleDtoCore;

import java.util.UUID;

public class UpdateTitleDtoIn {

    public String title;


    public UpdateTaleTitleDtoCore mapToTaleDomain(UUID taleId) {
        return new UpdateTaleTitleDtoCore(
                taleId,
                this.title
        );
    }

    public UpdateSceneTitleDtoCore mapToSceneDomain(UUID sceneId) {
        return new UpdateSceneTitleDtoCore(
                sceneId,
                this.title
        );
    }

}
