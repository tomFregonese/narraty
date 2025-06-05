package com.ynov.javaformation.narraty.dtos.editStory;

import com.ynov.javaformation.narraty.dtosCore.UpdateTaleTitleDtoCore;

import java.util.UUID;

public class UpdateTaleTitleDtoIn {

    public String title;


    public UpdateTaleTitleDtoCore mapToDomain(UUID taleId) {
        return new UpdateTaleTitleDtoCore(
                taleId,
                this.title
        );
    }

}
