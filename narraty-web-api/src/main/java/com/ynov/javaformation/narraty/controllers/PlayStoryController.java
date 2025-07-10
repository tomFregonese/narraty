package com.ynov.javaformation.narraty.controllers;

import com.ynov.javaformation.narraty.dtos.playStory.ReadSceneDtoOut;
import com.ynov.javaformation.narraty.dtos.playStory.SelectedChoiceDtoIn;
import com.ynov.javaformation.narraty.dtosCore.SelectedChoiceDtoCore;
import com.ynov.javaformation.narraty.exceptions.story.*;
import com.ynov.javaformation.narraty.models.Scene;
import com.ynov.javaformation.narraty.usecase.story.GetSceneToDisplayUseCase;
import com.ynov.javaformation.narraty.usecase.story.SaveUserChoiceAndFindNextSceneToDisplayUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "Play gallery")
@RequestMapping("/play-story")
public class PlayStoryController {

    private final GetSceneToDisplayUseCase getSceneToDisplayUseCase;
    private final SaveUserChoiceAndFindNextSceneToDisplayUseCase saveUserChoiceAndFindNextSceneToDisplayUseCase;

    @Autowired
    public PlayStoryController(
            GetSceneToDisplayUseCase getSceneToDisplayUseCase,
            SaveUserChoiceAndFindNextSceneToDisplayUseCase saveUserChoiceAndFindNextSceneToDisplayUseCase
            ) {
        this.getSceneToDisplayUseCase = getSceneToDisplayUseCase;
        this.saveUserChoiceAndFindNextSceneToDisplayUseCase = saveUserChoiceAndFindNextSceneToDisplayUseCase;
    }

    @GetMapping("/scene-to-display/{taleId}")
    public ResponseEntity<ReadSceneDtoOut> updateChoiceNextSceneId(@PathVariable("taleId") UUID taleId) {
        try {
            Scene scene = getSceneToDisplayUseCase.handle(taleId);
            return ResponseEntity.status(HttpStatus.OK).body(ReadSceneDtoOut.mapToDto(scene));
        } catch (TaleNotFoundException | SceneNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("save-user-choice/{taleId}")
    public ResponseEntity<ReadSceneDtoOut> saveUserChoiceAndFindNextSceneToDisplay(
            @PathVariable("taleId") UUID taleId,
            @RequestBody SelectedChoiceDtoIn selectedChoiceDtoIn) {
        try {
            SelectedChoiceDtoCore selectedChoice = selectedChoiceDtoIn.mapToDomain(taleId);
            Scene scene = saveUserChoiceAndFindNextSceneToDisplayUseCase.handle(selectedChoice);
            return ResponseEntity.status(HttpStatus.OK).body(ReadSceneDtoOut.mapToDto(scene));
        } catch (TaleNotFoundException | SceneNotFoundException | ChoiceNotFoundException |
                 UserTalePlayNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (UserUnauthorizedToMakeThisChoiceException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
