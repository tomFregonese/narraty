package com.ynov.javaformation.narraty.controllers;

import com.ynov.javaformation.narraty.dtos.publicStory.PublicTaleDtoOut;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.usecase.story.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Tag(name = "Story gallery")
@RequestMapping("/public-story")
public class PublicStoryController {

    private final GetAllPublishedTalesUseCase getAllPublishedTalesUseCase;

    @Autowired
    public PublicStoryController(
            GetAllPublishedTalesUseCase getAllPublishedTalesUseCase
            ) {
        this.getAllPublishedTalesUseCase = getAllPublishedTalesUseCase;
    }

    @GetMapping("/all-tales")
    public ResponseEntity<Collection<PublicTaleDtoOut>> updateChoiceNextSceneId() {
        try {
            Collection<Tale> tales = getAllPublishedTalesUseCase.handle(null);
            Collection<PublicTaleDtoOut> talesDto = tales.stream().map(PublicTaleDtoOut::mapToDto).toList();
            return ResponseEntity.status(HttpStatus.OK).body(talesDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
