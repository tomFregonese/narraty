package com.ynov.javaformation.narraty.dtos.editStory;

import com.ynov.javaformation.narraty.dtosCore.UpdateTitleDtoCore;

import java.util.UUID;

public class UpdateTitleDtoIn {

    public String title;


    public UpdateTitleDtoCore mapToDomain(UUID taleId) {
        return new UpdateTitleDtoCore(
                taleId,
                this.title
        );
    }

}
