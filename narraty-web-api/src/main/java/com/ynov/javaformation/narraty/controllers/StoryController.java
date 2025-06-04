package com.ynov.javaformation.narraty.controllers;

import com.ynov.javaformation.narraty.dtos.TaleDtoOut;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.security.Authorize;
import com.ynov.javaformation.narraty.usecase.story.SaveStoryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/story")
public class StoryController {

    private final SaveStoryUseCase saveStoryUseCase;

    @Autowired
    public StoryController(SaveStoryUseCase saveStory) {
        this.saveStoryUseCase = saveStory;
    }

    @Authorize
    @PostMapping("/save")
    public ResponseEntity<TaleDtoOut> saveStory() {
        try {
            Tale savedTale = saveStoryUseCase.handle(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(new TaleDtoOut(savedTale));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
