package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.dtosCore.OwningTestDtoCore;
import com.ynov.javaformation.narraty.dtosCore.UpdateSceneStatusDtoCore;
import com.ynov.javaformation.narraty.exceptions.story.SceneNotFoundException;
import com.ynov.javaformation.narraty.interfaces.daos.SceneDao;
import com.ynov.javaformation.narraty.models.Scene;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import com.ynov.javaformation.narraty.usecase.auth.GetAuthenticatedUserUseCase;
import com.ynov.javaformation.narraty.usecase.auth.IsOwnerUseCase;
import org.springframework.stereotype.Service;

@Service
public class UpdateSceneStatusUseCase implements IUseCase<UpdateSceneStatusDtoCore, Scene> {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final IsOwnerUseCase isOwnerUseCase;
    private final SceneDao dao;

    public UpdateSceneStatusUseCase(
            IsOwnerUseCase isOwnerUseCase,
            GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
            SceneDao dao) {
        this.isOwnerUseCase = isOwnerUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.dao = dao;
    }

    public Scene handle(UpdateSceneStatusDtoCore updateSceneStatusDtoCore) {

        User user = getAuthenticatedUserUseCase.handle(null);

        Scene scene = dao.findById(updateSceneStatusDtoCore.sceneId).orElseThrow(
                () -> new SceneNotFoundException("Scene: " + updateSceneStatusDtoCore.sceneId + " not found")
        );

        OwningTestDtoCore owningTestDtoCore = new OwningTestDtoCore(
                scene.taleId,
                user.id
        );

        isOwnerUseCase.handle(owningTestDtoCore);

        scene.status = updateSceneStatusDtoCore.status;

        return dao.save(scene);

    }

}
