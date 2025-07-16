package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.dtosCore.OwningTestDtoCore;
import com.ynov.javaformation.narraty.interfaces.daos.SceneDao;
import com.ynov.javaformation.narraty.interfaces.daos.TaleDao;
import com.ynov.javaformation.narraty.models.Scene;
import com.ynov.javaformation.narraty.models.SceneStatus;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import com.ynov.javaformation.narraty.usecase.auth.GetAuthenticatedUserUseCase;
import com.ynov.javaformation.narraty.usecase.auth.IsOwnerUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreateSceneUseCase implements IUseCase<UUID, Scene> {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final IsOwnerUseCase isOwnerUseCase;
    private final TaleDao taleDao;
    private final SceneDao sceneDao;

    public CreateSceneUseCase(
            IsOwnerUseCase isOwnerUseCase,
            GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
            TaleDao taleDao, SceneDao sceneDao) {
        this.isOwnerUseCase = isOwnerUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.taleDao = taleDao;
        this.sceneDao = sceneDao;
    }

    public Scene handle(UUID taleId) {

        User user = getAuthenticatedUserUseCase.handle(null);

        OwningTestDtoCore owningTestDtoCore = new OwningTestDtoCore(
                taleId,
                user.id
        );

        Tale tale = isOwnerUseCase.handle(owningTestDtoCore);

        Scene sceneToSave = Scene.builder()
                .title("Title of the Scene")
                .text("Text of the Scene")
                .taleId(tale.id)
                .status(SceneStatus.Default)
                .build();

        tale.updatedAt = LocalDateTime.now();
        taleDao.save(tale);

        return sceneDao.save(sceneToSave);

    }

}
