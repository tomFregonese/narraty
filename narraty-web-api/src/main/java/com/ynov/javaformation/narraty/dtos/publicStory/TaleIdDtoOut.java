package com.ynov.javaformation.narraty.dtos.publicStory;

import com.ynov.javaformation.narraty.models.Tale;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class TaleIdDtoOut {

    public UUID id;


    public static TaleIdDtoOut mapToDto(Tale tale) {
        return new TaleIdDtoOut(
                tale.id
        );
    }

}
