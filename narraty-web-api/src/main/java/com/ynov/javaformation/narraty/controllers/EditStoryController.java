package com.ynov.javaformation.narraty.controllers;

import com.ynov.javaformation.narraty.dtos.editStory.EditTaleDtoOut;
import com.ynov.javaformation.narraty.dtos.editStory.UpdateTaleDescriptionDtoIn;
import com.ynov.javaformation.narraty.dtos.editStory.UpdateTaleTitleDtoIn;
import com.ynov.javaformation.narraty.dtos.publicStory.PublicTaleDtoOut;
import com.ynov.javaformation.narraty.dtos.publicStory.TaleIdDtoOut;
import com.ynov.javaformation.narraty.dtosCore.UpdateTaleDescriptionDtoCore;
import com.ynov.javaformation.narraty.dtosCore.UpdateTaleTitleDtoCore;
import com.ynov.javaformation.narraty.exceptions.story.NotTaleAuthorException;
import com.ynov.javaformation.narraty.exceptions.story.TaleNotFoundException;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.security.Authorize;
import com.ynov.javaformation.narraty.usecase.story.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@Tag(name = "Story edition")
@RequestMapping("/edit-story")
public class EditStoryController {

    private final GetAllUserTalesUseCase getAllUserTalesUseCase;
    private final CreateTaleUseCase createTaleUseCase;
    private final GetFullTaleUseCase getFullTaleUseCase;
    private final UpdateTaleTitleUseCase updateTaleTitleUseCase;
    private final UpdateTaleDescriptionUseCase updateTaleDescriptionUseCase;
    //private final UpdateTaleStatusUseCase updateTaleStatusUseCase;
    private final DeleteTaleUseCase deleteTaleUseCase;

    @Autowired
    public EditStoryController(
            GetAllUserTalesUseCase getAllUserTalesUseCase,
            CreateTaleUseCase saveStory,
            GetFullTaleUseCase getFullTaleUseCase,
            UpdateTaleTitleUseCase updateTaleTitleUseCase,
            UpdateTaleDescriptionUseCase updateTaleDescriptionUseCase,
            //UpdateTaleStatusUseCase updateTaleStatusUseCase,
            DeleteTaleUseCase deleteTaleUseCase) {
        this.getAllUserTalesUseCase = getAllUserTalesUseCase;
        this.createTaleUseCase = saveStory;
        this.getFullTaleUseCase = getFullTaleUseCase;
        this.updateTaleTitleUseCase = updateTaleTitleUseCase;
        this.updateTaleDescriptionUseCase = updateTaleDescriptionUseCase;
        //this.updateTaleStatusUseCase = updateTaleStatusUseCase;
        this.deleteTaleUseCase = deleteTaleUseCase;
    }

    @Authorize
    @GetMapping("/my-tales")
    public ResponseEntity<Collection<PublicTaleDtoOut>> getMyTales() {
        try {
            Collection<Tale> tales = getAllUserTalesUseCase.handle(null);
            Collection<PublicTaleDtoOut> talesDto = tales.stream().map(PublicTaleDtoOut::mapToDto).toList();
            return ResponseEntity.status(HttpStatus.OK).body(talesDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
    @GetMapping("/tale/{taleId}/info")
    public ResponseEntity<EditTaleDtoOut> getTale(@PathVariable UUID taleId) {
        try {
            Tale tale = getFullTaleUseCase.handle(taleId);
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
    @PutMapping("/tale/{taleId}/title")
    public ResponseEntity<EditTaleDtoOut> updateTaleTitle(@PathVariable UUID taleId, @RequestBody UpdateTaleTitleDtoIn updateTaleTitleDtoIn) {
        try {
            UpdateTaleTitleDtoCore updateTaleTitleDtoCore = updateTaleTitleDtoIn.mapToDomain(taleId);
            Tale tale = updateTaleTitleUseCase.handle(updateTaleTitleDtoCore);
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

    @Authorize
    @PutMapping("/tale/{taleId}/desc")
    public ResponseEntity<EditTaleDtoOut> updateTaleDescription(
            @PathVariable UUID taleId,
            @RequestBody UpdateTaleDescriptionDtoIn updatetaleDescriptionDtoIn) {
        try {
            UpdateTaleDescriptionDtoCore updateTaleDescriptionDtoCore = updatetaleDescriptionDtoIn.mapToDomain(taleId);
            Tale tale = updateTaleDescriptionUseCase.handle(updateTaleDescriptionDtoCore);
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

    @Authorize
    @DeleteMapping("/tale/{taleId}")
    public ResponseEntity<Void> deleteTale(@PathVariable UUID taleId) {
        try {
            deleteTaleUseCase.handle(taleId);
            return ResponseEntity.status(HttpStatus.OK).build();
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
