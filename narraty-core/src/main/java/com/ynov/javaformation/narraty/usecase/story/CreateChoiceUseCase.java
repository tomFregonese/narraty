package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.dtosCore.OwningTestDtoCore;
import com.ynov.javaformation.narraty.exceptions.story.SceneNotFoundException;
import com.ynov.javaformation.narraty.interfaces.daos.ChoiceDao;
import com.ynov.javaformation.narraty.interfaces.daos.SceneDao;
import com.ynov.javaformation.narraty.interfaces.daos.TaleDao;
import com.ynov.javaformation.narraty.models.*;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import com.ynov.javaformation.narraty.usecase.auth.GetAuthenticatedUserUseCase;
import com.ynov.javaformation.narraty.usecase.auth.IsOwnerUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreateChoiceUseCase implements IUseCase<UUID, Choice> {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final IsOwnerUseCase isOwnerUseCase;
    private final TaleDao taleDao;
    private final SceneDao sceneDao;
    private final ChoiceDao choiceDao;

    public CreateChoiceUseCase(
            IsOwnerUseCase isOwnerUseCase,
            GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
            TaleDao taleDao, SceneDao sceneDao, ChoiceDao choiceDao) {
        this.isOwnerUseCase = isOwnerUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.taleDao = taleDao;
        this.sceneDao = sceneDao;
        this.choiceDao = choiceDao;
    }

    public Choice handle(UUID sceneId) {

        User user = getAuthenticatedUserUseCase.handle(null);

        Scene scene = sceneDao.findById(sceneId).orElseThrow(
                () -> new SceneNotFoundException("Scene: " + sceneId + " not found")
        );

        OwningTestDtoCore owningTestDtoCore = new OwningTestDtoCore(
                scene.taleId,
                user.id
        );

        Tale tale = isOwnerUseCase.handle(owningTestDtoCore);

        Choice choiceToSave = Choice.builder()
                .text("Do this")
                .sceneId(sceneId)
                .build();

        tale.updatedAt = LocalDateTime.now();
        taleDao.save(tale);

        return choiceDao.save(choiceToSave);

    }

}
