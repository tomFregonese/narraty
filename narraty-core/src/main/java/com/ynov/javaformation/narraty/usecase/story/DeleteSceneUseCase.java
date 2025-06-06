package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.dtosCore.OwningTestDtoCore;
import com.ynov.javaformation.narraty.exceptions.story.SceneNotFoundException;
import com.ynov.javaformation.narraty.interfaces.daos.SceneDao;
import com.ynov.javaformation.narraty.models.Scene;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import com.ynov.javaformation.narraty.usecase.auth.GetAuthenticatedUserUseCase;
import com.ynov.javaformation.narraty.usecase.auth.IsOwnerUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteSceneUseCase implements IUseCase<UUID, Void> {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final IsOwnerUseCase isOwnerUseCase;
    private final SceneDao dao;

    public DeleteSceneUseCase(
            IsOwnerUseCase isOwnerUseCase,
            GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
            SceneDao dao) {
        this.isOwnerUseCase = isOwnerUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.dao = dao;
    }

    public Void handle(UUID sceneId) {

        User user = getAuthenticatedUserUseCase.handle(null);

        Scene scene = dao.findById(sceneId).orElseThrow(
                () -> new SceneNotFoundException("Scene: " + sceneId + " not found")
        );

        OwningTestDtoCore owningTestDtoCore = new OwningTestDtoCore(
                scene.taleId,
                user.id
        );

        isOwnerUseCase.handle(owningTestDtoCore);

        dao.deleteById(sceneId);

        return null;

    }

}
