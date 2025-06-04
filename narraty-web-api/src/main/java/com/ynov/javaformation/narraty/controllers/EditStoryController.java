package com.ynov.javaformation.narraty.controllers;

import com.ynov.javaformation.narraty.dtos.editStory.EditTaleDtoOut;
import com.ynov.javaformation.narraty.dtos.publicStory.TaleIdDtoOut;
import com.ynov.javaformation.narraty.exceptions.story.TaleNotFoundException;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.security.Authorize;
import com.ynov.javaformation.narraty.usecase.story.CreateTaleUseCase;
import com.ynov.javaformation.narraty.usecase.story.GetFullTaleUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/story")
public class EditStoryController {

    private final CreateTaleUseCase createTaleUseCase;
    private final GetFullTaleUseCase getFullTaleUseCase;

    @Autowired
    public EditStoryController(CreateTaleUseCase saveStory, GetFullTaleUseCase getFullTaleUseCase) {
        this.createTaleUseCase = saveStory;
        this.getFullTaleUseCase = getFullTaleUseCase;
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

    @Authorize
    @GetMapping("/get-tale/{id}")
    public ResponseEntity<EditTaleDtoOut> getTale(@PathVariable UUID id) {
        try {
            Tale tale = getFullTaleUseCase.handle(id);
            return ResponseEntity.status(HttpStatus.OK).body(EditTaleDtoOut.mapToDto(tale));
        } catch (TaleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
