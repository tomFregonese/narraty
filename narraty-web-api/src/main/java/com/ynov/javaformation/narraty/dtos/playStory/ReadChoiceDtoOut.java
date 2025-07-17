package com.ynov.javaformation.narraty.dtos.playStory;

import com.ynov.javaformation.narraty.models.Choice;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class ReadChoiceDtoOut {

    public UUID id;

    public String txt;


    public static ReadChoiceDtoOut mapToDto(Choice choice) {
        return new ReadChoiceDtoOut(
                choice.id,
                choice.text
        );
    }

}
