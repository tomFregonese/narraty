package com.ynov.javaformation.narraty.dtos.playStory;

import com.ynov.javaformation.narraty.models.Scene;
import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
public class ReadSceneDtoOut {

    public String ttl;

    public String txt;

    public Collection<ReadChoiceDtoOut> chcs;


    public static ReadSceneDtoOut mapToDto(Scene scene) {
        return new ReadSceneDtoOut(
                scene.title,
                scene.text,
                scene.choices == null ? null :
                    scene.choices.stream()
                        .map(ReadChoiceDtoOut::mapToDto)
                        .toList()
        );
    }

}
