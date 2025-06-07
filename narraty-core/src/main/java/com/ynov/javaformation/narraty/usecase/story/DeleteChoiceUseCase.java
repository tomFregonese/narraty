package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.dtosCore.OwningTestDtoCore;
import com.ynov.javaformation.narraty.exceptions.story.ChoiceNotFoundException;
import com.ynov.javaformation.narraty.exceptions.story.SceneNotFoundException;
import com.ynov.javaformation.narraty.interfaces.daos.ChoiceDao;
import com.ynov.javaformation.narraty.interfaces.daos.SceneDao;
import com.ynov.javaformation.narraty.models.Choice;
import com.ynov.javaformation.narraty.models.Scene;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import com.ynov.javaformation.narraty.usecase.auth.GetAuthenticatedUserUseCase;
import com.ynov.javaformation.narraty.usecase.auth.IsOwnerUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteChoiceUseCase implements IUseCase<UUID, Void> {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final IsOwnerUseCase isOwnerUseCase;
    private final SceneDao sceneDao;
    private final ChoiceDao choiceDao;

    public DeleteChoiceUseCase(
            IsOwnerUseCase isOwnerUseCase,
            GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
            SceneDao sceneDao,
            ChoiceDao choiceDao) {
        this.isOwnerUseCase = isOwnerUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.sceneDao = sceneDao;
        this.choiceDao = choiceDao;
    }

    public Void handle(UUID choiceId) {

        User user = getAuthenticatedUserUseCase.handle(null);

        Choice choice = choiceDao.findById(choiceId).orElseThrow(
                () -> new ChoiceNotFoundException("Choice: " + choiceId + " not found")
        );

        Scene scene = sceneDao.findById(choice.sceneId).orElseThrow(
                () -> new SceneNotFoundException("Scene: " + choice.sceneId + " not found")
        );

        OwningTestDtoCore owningTestDtoCore = new OwningTestDtoCore(
                scene.taleId,
                user.id
        );

        isOwnerUseCase.handle(owningTestDtoCore);

        choiceDao.deleteById(choiceId);

        return null;

    }

}
