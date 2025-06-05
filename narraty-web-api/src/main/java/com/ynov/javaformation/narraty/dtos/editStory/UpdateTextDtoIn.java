package com.ynov.javaformation.narraty.dtos.editStory;

import com.ynov.javaformation.narraty.dtosCore.UpdateSceneTextDtoCore;

import java.util.UUID;

public class UpdateTextDtoIn {

    public String txt;

    public UpdateSceneTextDtoCore mapToSceneDomain(UUID sceneId) {
        return new UpdateSceneTextDtoCore(
                sceneId,
                this.txt
        );
    }


    /*public UpdateChoiceTextDtoCore mapToTaleDomain(UUID taleId) {
        return new UpdateChoiceTextDtoCore(
                taleId,
                this.txt
        );
    }*/

}
