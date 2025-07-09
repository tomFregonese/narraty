package com.ynov.javaformation.narraty.controllers;

import com.ynov.javaformation.narraty.dtos.playStory.ReadSceneDtoOut;
import com.ynov.javaformation.narraty.models.Scene;
import com.ynov.javaformation.narraty.usecase.story.GetSceneToDisplayUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Tag(name = "Play gallery")
@RequestMapping("/play-story")
public class PlayStoryController {

    private final GetSceneToDisplayUseCase getSceneToDisplayUseCase;

    @Autowired
    public PlayStoryController(
            GetSceneToDisplayUseCase getSceneToDisplayUseCase
            ) {
        this.getSceneToDisplayUseCase = getSceneToDisplayUseCase;
    }

    @GetMapping("/scene-to-display/{taleId}")
    public ResponseEntity<ReadSceneDtoOut> updateChoiceNextSceneId(@PathVariable("taleId") UUID taleId) {
        try {
            Scene scene = getSceneToDisplayUseCase.handle(taleId);
            return ResponseEntity.status(HttpStatus.OK).body(ReadSceneDtoOut.mapToDto(scene));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
