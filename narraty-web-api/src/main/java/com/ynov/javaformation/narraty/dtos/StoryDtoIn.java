package com.ynov.javaformation.narraty.dtos;

import com.ynov.javaformation.narraty.models.Story;
import lombok.Data;

import java.util.UUID;

@Data
public class StoryDtoIn {

    String ttl;
    String dsc;

    public Story toModel() {
        return new Story(
                UUID.randomUUID(),
                ttl,
                dsc,
                null
        );
    }

}
