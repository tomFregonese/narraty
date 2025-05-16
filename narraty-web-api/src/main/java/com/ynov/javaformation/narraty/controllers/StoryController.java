package com.ynov.javaformation.narraty.controllers;

import com.ynov.javaformation.narraty.dtos.StoryDtoIn;
import com.ynov.javaformation.narraty.dtos.StoryDtoOut;
import com.ynov.javaformation.narraty.models.Story;
import com.ynov.javaformation.narraty.usecase.story.SaveStoryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/story")
public class StoryController {

    private final SaveStoryUseCase saveStoryUseCase;

    @Autowired
    public StoryController(SaveStoryUseCase saveStory) { //TODO fix error here
        this.saveStoryUseCase = saveStory;
    }

    @PostMapping("/save")
    public ResponseEntity<StoryDtoOut> saveStory(@RequestBody StoryDtoIn incomingStory) {
        try {
            Story storyToSave = incomingStory.toModel();
            Story savedStory = saveStoryUseCase.handle(storyToSave);
            return ResponseEntity.status(HttpStatus.CREATED).body(new StoryDtoOut(savedStory));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
