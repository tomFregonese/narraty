package com.ynov.javaformation.narraty.dtos.editStory;

import com.ynov.javaformation.narraty.dtosCore.UpdateTaleDescriptionDtoCore;

import java.util.UUID;

public class UpdateDescriptionDtoIn {

    public String dsc;


    public UpdateTaleDescriptionDtoCore mapToDomain(UUID taleId) {
        return new UpdateTaleDescriptionDtoCore(
                taleId,
                this.dsc
        );
    }

}
