package com.ynov.javaformation.narraty.dtos.editStory;

import com.ynov.javaformation.narraty.models.Scene;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class EditSceneDtoOut {

    UUID id;

    String ttl;

    String txt;

    int stts;

    Collection<EditChoiceDtoOut> chcs;


    public static EditSceneDtoOut mapToDto(Scene scene) {
        return new EditSceneDtoOut(
            scene.id,
            scene.title,
            scene.text,
            scene.status.ordinal(),
            scene.choices == null ? null :
                scene.choices.stream()
                    .map(EditChoiceDtoOut::mapToDto)
                    .toList()
        );
    }

}
