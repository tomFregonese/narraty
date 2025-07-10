package com.ynov.javaformation.narraty.dtos.playStory;

import com.ynov.javaformation.narraty.dtosCore.SelectedChoiceDtoCore;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class SelectedChoiceDtoIn {

    public UUID choiceId;

    public SelectedChoiceDtoCore mapToDomain(UUID taleId) {
        return new SelectedChoiceDtoCore(
                taleId,
                this.choiceId
        );
    }


}