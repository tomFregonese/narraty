package com.ynov.javaformation.narraty.dtos.editStory;

import com.ynov.javaformation.narraty.models.Choice;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class EditChoiceDtoOut {

    UUID id;

    String txt;

    UUID nxtScnId;


    public static EditChoiceDtoOut mapToDto(Choice choice) {
        return new EditChoiceDtoOut(
            choice.id,
            choice.text,
            choice.nextSceneId
        );
    }

}
