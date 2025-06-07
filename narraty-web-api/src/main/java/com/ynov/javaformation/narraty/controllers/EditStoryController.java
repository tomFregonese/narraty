package com.ynov.javaformation.narraty.controllers;

import com.ynov.javaformation.narraty.dtos.editStory.*;
import com.ynov.javaformation.narraty.dtos.publicStory.PublicTaleDtoOut;
import com.ynov.javaformation.narraty.dtos.publicStory.TaleIdDtoOut;
import com.ynov.javaformation.narraty.dtosCore.*;
import com.ynov.javaformation.narraty.exceptions.story.*;
import com.ynov.javaformation.narraty.models.Choice;
import com.ynov.javaformation.narraty.models.Scene;
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
    private final UpdateTaleStatusUseCase updateTaleStatusUseCase;
    private final DeleteTaleUseCase deleteTaleUseCase;

    private final CreateSceneUseCase createSceneUseCase;
    private final UpdateSceneTitleUseCase updateSceneTitleUseCase;
    private final UpdateSceneTextUseCase updateSceneTextUseCase;
    private final UpdateSceneStatusUseCase updateSceneStatusUseCase;
    private final DeleteSceneUseCase deleteSceneUseCase;

    private final CreateChoiceUseCase createChoiceUseCase;
    private final UpdateChoiceTextUseCase updateChoiceTextUseCase;
    private final UpdateChoiceNextSceneIdUseCase updateChoiceNextSceneIdUseCase;
    private final DeleteChoiceUseCase deleteChoiceUseCase;

    @Autowired
    public EditStoryController(
            GetAllUserTalesUseCase getAllUserTalesUseCase,

            CreateTaleUseCase saveStory,
            GetFullTaleUseCase getFullTaleUseCase,
            UpdateTaleTitleUseCase updateTaleTitleUseCase,
            UpdateTaleDescriptionUseCase updateTaleDescriptionUseCase,
            UpdateTaleStatusUseCase updateTaleStatusUseCase,
            DeleteTaleUseCase deleteTaleUseCase,

            CreateSceneUseCase createSceneUseCase,
            UpdateSceneTitleUseCase updateSceneTitleUseCase,
            UpdateSceneTextUseCase updateSceneTextUseCase,
            UpdateSceneStatusUseCase updateSceneStatusUseCase, DeleteSceneUseCase deleteSceneUseCase,

            CreateChoiceUseCase createChoiceUseCase,
            UpdateChoiceTextUseCase updateChoiceTextUseCase,
            UpdateChoiceNextSceneIdUseCase updateChoiceNextSceneIdUseCase,
            DeleteChoiceUseCase deleteChoiceUseCase) {
        this.getAllUserTalesUseCase = getAllUserTalesUseCase;

        this.createTaleUseCase = saveStory;
        this.getFullTaleUseCase = getFullTaleUseCase;
        this.updateTaleTitleUseCase = updateTaleTitleUseCase;
        this.updateTaleDescriptionUseCase = updateTaleDescriptionUseCase;
        this.updateTaleStatusUseCase = updateTaleStatusUseCase;
        this.deleteTaleUseCase = deleteTaleUseCase;

        this.createSceneUseCase = createSceneUseCase;
        this.updateSceneTitleUseCase = updateSceneTitleUseCase;
        this.updateSceneTextUseCase = updateSceneTextUseCase;
        this.updateSceneStatusUseCase = updateSceneStatusUseCase;
        this.deleteSceneUseCase = deleteSceneUseCase;

        this.createChoiceUseCase = createChoiceUseCase;
        this.updateChoiceTextUseCase = updateChoiceTextUseCase;
        this.updateChoiceNextSceneIdUseCase = updateChoiceNextSceneIdUseCase;
        this.deleteChoiceUseCase = deleteChoiceUseCase;
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (NotTaleAuthorException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Authorize
    @PutMapping("/tale/{taleId}/title")
    public ResponseEntity<EditTaleDtoOut> updateTaleTitle(@PathVariable UUID taleId, @RequestBody UpdateTitleDtoIn updateTitleDtoIn) {
        try {
            UpdateTaleTitleDtoCore updateTaleTitleDtoCore = updateTitleDtoIn.mapToTaleDomain(taleId);
            Tale tale = updateTaleTitleUseCase.handle(updateTaleTitleDtoCore);
            return ResponseEntity.status(HttpStatus.OK).body(EditTaleDtoOut.mapToDto(tale));
        } catch (TaleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
            @RequestBody UpdateDescriptionDtoIn updatetaleDescriptionDtoIn) {
        try {
            UpdateTaleDescriptionDtoCore updateTaleDescriptionDtoCore = updatetaleDescriptionDtoIn.mapToDomain(taleId);
            Tale tale = updateTaleDescriptionUseCase.handle(updateTaleDescriptionDtoCore);
            return ResponseEntity.status(HttpStatus.OK).body(EditTaleDtoOut.mapToDto(tale));
        } catch (TaleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (NotTaleAuthorException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Authorize
    @PutMapping("tale/{taleId}/status")
    public ResponseEntity<EditTaleDtoOut> updateTaleStatus(
            @PathVariable UUID taleId,
            @RequestBody UpdateStatusDtoIn updateStatusDtoIn) {
        try {
            UpdateTaleStatusDtoCore updateTaleStatusDtoCore = updateStatusDtoIn.mapToTaleDomain(taleId);
            Tale tale = updateTaleStatusUseCase.handle(updateTaleStatusDtoCore);
            return ResponseEntity.status(HttpStatus.OK).body(EditTaleDtoOut.mapToDto(tale));
        } catch (UnprocessableTaleException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (TaleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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

    @Authorize
    @PostMapping("/create-scene/{taleId}")
    public ResponseEntity<EditSceneDtoOut> createScene(@PathVariable UUID taleId) {
        try {
            Scene scene = createSceneUseCase.handle(taleId);
            return ResponseEntity.status(HttpStatus.CREATED).body(EditSceneDtoOut.mapToDto(scene));
        } catch (TaleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (NotTaleAuthorException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Authorize
    @PutMapping("/scene/{sceneId}/title")
    public ResponseEntity<EditSceneDtoOut> updateSceneTitle(
            @PathVariable UUID sceneId,
            @RequestBody UpdateTitleDtoIn updateTitleDtoIn) {
        try {
            UpdateSceneTitleDtoCore updateSceneTitleDtoCore = updateTitleDtoIn.mapToSceneDomain(sceneId);
            Scene scene = updateSceneTitleUseCase.handle(updateSceneTitleDtoCore);
            return ResponseEntity.status(HttpStatus.OK).body(EditSceneDtoOut.mapToDto(scene));
        } catch (NotTaleAuthorException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (TaleNotFoundException | SceneNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Authorize
    @PutMapping("/scene/{sceneId}/text")
    public ResponseEntity<EditSceneDtoOut> updateSceneText(
            @PathVariable UUID sceneId,
            @RequestBody UpdateTextDtoIn updateTextDtoIn) {
        try {
            UpdateSceneTextDtoCore updateSceneTextDtoCore = updateTextDtoIn.mapToSceneDomain(sceneId);
            Scene scene = updateSceneTextUseCase.handle(updateSceneTextDtoCore);
            return ResponseEntity.status(HttpStatus.OK).body(EditSceneDtoOut.mapToDto(scene));
        } catch (NotTaleAuthorException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (TaleNotFoundException | SceneNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Authorize
    @PutMapping("/scene/{sceneId}/status")
    public ResponseEntity<EditSceneDtoOut> updateSceneStatus(
            @PathVariable UUID sceneId,
            @RequestBody UpdateStatusDtoIn updateStatusDtoIn) {
        try {
            UpdateSceneStatusDtoCore updateSceneStatusDtoCore = updateStatusDtoIn.mapToSceneDomain(sceneId);
            Scene scene = updateSceneStatusUseCase.handle(updateSceneStatusDtoCore);
            return ResponseEntity.status(HttpStatus.OK).body(EditSceneDtoOut.mapToDto(scene));
        } catch (NotTaleAuthorException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (SceneStatusWithThisIdDoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (TaleNotFoundException | SceneNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Authorize
    @DeleteMapping("/scene/{sceneId}")
    public ResponseEntity<Void> deleteScene(@PathVariable UUID sceneId) {
        try {
            deleteSceneUseCase.handle(sceneId);
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

    @Authorize
    @PostMapping("create-choice/{sceneId}")
    public ResponseEntity<EditChoiceDtoOut> createChoice(@PathVariable UUID sceneId) {
        try {
            Choice choice = createChoiceUseCase.handle(sceneId);
            return ResponseEntity.status(HttpStatus.CREATED).body(EditChoiceDtoOut.mapToDto(choice));
        } catch (SceneNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (NotTaleAuthorException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Authorize
    @PutMapping("/choice/{choiceId}/text")
    public ResponseEntity<EditChoiceDtoOut> updateChoiceText(
            @PathVariable UUID choiceId,
            @RequestBody UpdateTextDtoIn updateTextDtoIn) {
        try {
            UpdateChoiceTextDtoCore updateChoiceTextDtoCore = updateTextDtoIn.mapToChoiceDomain(choiceId);
            Choice choice = updateChoiceTextUseCase.handle(updateChoiceTextDtoCore);
            return ResponseEntity.status(HttpStatus.OK).body(EditChoiceDtoOut.mapToDto(choice));
        } catch (NotTaleAuthorException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (SceneNotFoundException | ChoiceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Authorize
    @PutMapping("/choice/{choiceId}/next-scene")
    public ResponseEntity<EditChoiceDtoOut> updateChoiceNextSceneId(
            @PathVariable UUID choiceId,
            @RequestBody UpdateNextSceneIdDtoIn updateNextSceneIdDtoIn) {
        try {
            UpdateChoiceNextSceneIdDtoCore updateChoiceNextSceneIdDtoCore = updateNextSceneIdDtoIn.mapToDomain(choiceId);
            Choice choice = updateChoiceNextSceneIdUseCase.handle(updateChoiceNextSceneIdDtoCore);
            return ResponseEntity.status(HttpStatus.OK).body(EditChoiceDtoOut.mapToDto(choice));
        } catch (NotTaleAuthorException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (SceneNotFoundException | ChoiceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (TargetedSceneDoesNotBelongToTheSaveTaleAsTheChoice e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Authorize
    @DeleteMapping("/choice/{choiceId}")
    public ResponseEntity<Void> deleteChoice(@PathVariable UUID choiceId) {
        try {
            deleteChoiceUseCase.handle(choiceId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (SceneNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (NotTaleAuthorException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (ChoiceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
