package com.ynov.javaformation.narraty.dtos.editStory;

import com.ynov.javaformation.narraty.dtosCore.UpdateTaleDescriptionDtoCore;

import java.util.UUID;

public class UpdateTaleDescriptionDtoIn {

    public String description;


    public UpdateTaleDescriptionDtoCore mapToDomain(UUID taleId) {
        return new UpdateTaleDescriptionDtoCore(
                taleId,
                this.description
        );
    }

}
