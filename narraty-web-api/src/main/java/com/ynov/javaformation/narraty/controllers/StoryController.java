package com.ynov.javaformation.narraty.controllers;

import com.ynov.javaformation.narraty.dtos.publicStory.TaleIdDtoOut;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.security.Authorize;
import com.ynov.javaformation.narraty.usecase.story.CreateTaleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/story")
public class StoryController {

    private final CreateTaleUseCase createTaleUseCase;

    @Autowired
    public StoryController(CreateTaleUseCase saveStory) {
        this.createTaleUseCase = saveStory;
    }

    @Authorize
    @PostMapping("/create-tale")
    public ResponseEntity<TaleIdDtoOut> createTale() {
        try {
            Tale savedTale = createTaleUseCase.handle(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(TaleIdDtoOut.mapToDto(savedTale));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
