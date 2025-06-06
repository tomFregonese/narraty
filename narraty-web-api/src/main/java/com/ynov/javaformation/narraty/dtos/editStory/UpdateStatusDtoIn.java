package com.ynov.javaformation.narraty.dtos.editStory;

import com.ynov.javaformation.narraty.dtosCore.UpdateSceneStatusDtoCore;
import com.ynov.javaformation.narraty.dtosCore.UpdateTaleStatusDtoCore;
import com.ynov.javaformation.narraty.exceptions.story.SceneStatusWithThisIdDoesNotExistException;
import com.ynov.javaformation.narraty.exceptions.story.TaleStatusWithThisIdDoesNotExistException;
import com.ynov.javaformation.narraty.models.SceneStatus;
import com.ynov.javaformation.narraty.models.TaleStatus;

import java.util.UUID;

public class UpdateStatusDtoIn {

    public int stts;


    /*public UpdateTaleStatusDtoCore mapToTaleDomain(UUID sceneId) {
        TaleStatus[] statuses = TaleStatus.values();
        if (stts < 0 || stts >= statuses.length) {
            throw new TaleStatusWithThisIdDoesNotExistException("Invalid SceneStatus ordinal: " + stts);
        }
        return new UpdateTaleStatusDtoCore(
                sceneId,
                statuses[stts]
        );
    }*/

    public UpdateSceneStatusDtoCore mapToSceneDomain(UUID sceneId) {
        SceneStatus[] statuses = SceneStatus.values();
        if (stts < 0 || stts >= statuses.length) {
            throw new SceneStatusWithThisIdDoesNotExistException("Invalid SceneStatus ordinal: " + stts);
        }
        return new UpdateSceneStatusDtoCore(
            sceneId,
            statuses[stts]
        );
    }

}
