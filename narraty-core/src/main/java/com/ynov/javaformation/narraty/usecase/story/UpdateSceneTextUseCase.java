package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.dtosCore.OwningTestDtoCore;
import com.ynov.javaformation.narraty.dtosCore.UpdateSceneTextDtoCore;
import com.ynov.javaformation.narraty.exceptions.story.SceneNotFoundException;
import com.ynov.javaformation.narraty.interfaces.daos.SceneDao;
import com.ynov.javaformation.narraty.models.Scene;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import com.ynov.javaformation.narraty.usecase.auth.GetAuthenticatedUserUseCase;
import com.ynov.javaformation.narraty.usecase.auth.IsOwnerUseCase;
import org.springframework.stereotype.Service;

@Service
public class UpdateSceneTextUseCase implements IUseCase<UpdateSceneTextDtoCore, Scene> {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final IsOwnerUseCase isOwnerUseCase;
    private final SceneDao dao;

    public UpdateSceneTextUseCase(
            IsOwnerUseCase isOwnerUseCase,
            GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
            SceneDao dao) {
        this.isOwnerUseCase = isOwnerUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.dao = dao;
    }

    public Scene handle(UpdateSceneTextDtoCore updateSceneTextDtoCore) {

        User user = getAuthenticatedUserUseCase.handle(null);

        Scene scene = dao.findById(updateSceneTextDtoCore.sceneId).orElseThrow(
                () -> new SceneNotFoundException("Scene: " + updateSceneTextDtoCore.sceneId + " not found")
        );

        OwningTestDtoCore owningTestDtoCore = new OwningTestDtoCore(
                scene.taleId,
                user.id
        );

        isOwnerUseCase.handle(owningTestDtoCore);

        scene.text = updateSceneTextDtoCore.text;

        return dao.save(scene);

    }

}
