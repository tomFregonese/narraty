package com.ynov.javaformation.narraty.controllers;

import com.ynov.javaformation.narraty.dtos.editStory.EditTaleDtoOut;
import com.ynov.javaformation.narraty.dtos.editStory.UpdateTitleDtoIn;
import com.ynov.javaformation.narraty.dtos.publicStory.TaleIdDtoOut;
import com.ynov.javaformation.narraty.dtosCore.UpdateTitleDtoCore;
import com.ynov.javaformation.narraty.exceptions.story.NotTaleAuthorException;
import com.ynov.javaformation.narraty.exceptions.story.TaleNotFoundException;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.security.Authorize;
import com.ynov.javaformation.narraty.usecase.story.CreateTaleUseCase;
import com.ynov.javaformation.narraty.usecase.story.GetFullTaleUseCase;
import com.ynov.javaformation.narraty.usecase.story.UpdateTaleTitleUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Story edition")
@RequestMapping("/edit-story")
public class EditStoryController {

    private final CreateTaleUseCase createTaleUseCase;
    private final GetFullTaleUseCase getFullTaleUseCase;
    private final UpdateTaleTitleUseCase updateTaleTitleUseCase;

    @Autowired
    public EditStoryController(CreateTaleUseCase saveStory, GetFullTaleUseCase getFullTaleUseCase, UpdateTaleTitleUseCase updateTaleTitleUseCase) {
        this.createTaleUseCase = saveStory;
        this.getFullTaleUseCase = getFullTaleUseCase;
        this.updateTaleTitleUseCase = updateTaleTitleUseCase;
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
    @GetMapping("/tale/{id}/info")
    public ResponseEntity<EditTaleDtoOut> getTale(@PathVariable UUID id) {
        try {
            Tale tale = getFullTaleUseCase.handle(id);
            return ResponseEntity.status(HttpStatus.OK).body(EditTaleDtoOut.mapToDto(tale));
        } catch (TaleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (NotTaleAuthorException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Authorize
    @PutMapping("/tale/{id}/title")
    public ResponseEntity<EditTaleDtoOut> updateTaleTitle(@PathVariable UUID id, @RequestBody UpdateTitleDtoIn updateTitleDtoIn) {
        try {
            UpdateTitleDtoCore updateTitleDtoCore = updateTitleDtoIn.mapToDomain(id);
            Tale tale = updateTaleTitleUseCase.handle(updateTitleDtoCore);
            return ResponseEntity.status(HttpStatus.OK).body(EditTaleDtoOut.mapToDto(tale));
        } catch (TaleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (NotTaleAuthorException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
