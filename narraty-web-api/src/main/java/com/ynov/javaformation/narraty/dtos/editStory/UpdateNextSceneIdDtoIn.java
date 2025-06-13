package com.ynov.javaformation.narraty.dtos.editStory;

import com.ynov.javaformation.narraty.dtosCore.UpdateChoiceNextSceneIdDtoCore;

import java.util.UUID;

public class UpdateNextSceneIdDtoIn {

    public UUID nxtScnId;


    public UpdateChoiceNextSceneIdDtoCore mapToDomain(UUID choiceID) {
        return new UpdateChoiceNextSceneIdDtoCore(
            choiceID,
            nxtScnId
        );
    }

}
