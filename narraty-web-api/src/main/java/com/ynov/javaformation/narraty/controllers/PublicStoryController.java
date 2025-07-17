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
import java.util.UUID;

@RestController
@Tag(name = "Story gallery")
@RequestMapping("/public-story")
public class PublicStoryController {

    private final GetAllPublishedTalesUseCase getAllPublishedTalesUseCase;
    private final GetTaleByIdUseCase getTaleByIdUseCase;

    @Autowired
    public PublicStoryController(
            GetAllPublishedTalesUseCase getAllPublishedTalesUseCase,
            GetTaleByIdUseCase getTaleByIdUseCase
            ) {
        this.getAllPublishedTalesUseCase = getAllPublishedTalesUseCase;
        this.getTaleByIdUseCase = getTaleByIdUseCase;
    }

    @GetMapping("/all-tales")
    public ResponseEntity<Collection<PublicTaleDtoOut>> getAllPublishedTales() {
        try {
            Collection<Tale> tales = getAllPublishedTalesUseCase.handle(null);
            Collection<PublicTaleDtoOut> talesDto = tales.stream().map(PublicTaleDtoOut::mapToDto).toList();
            return ResponseEntity.status(HttpStatus.OK).body(talesDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/tale/{id}")
    public ResponseEntity<PublicTaleDtoOut> getTaleById(@PathVariable("id") UUID id) {
        try {
            Tale tale = getTaleByIdUseCase.handle(id);
            if (tale != null) {
                return ResponseEntity.status(HttpStatus.OK).body(PublicTaleDtoOut.mapToDto(tale));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
