package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.dtosCore.OwningTestDtoCore;
import com.ynov.javaformation.narraty.dtosCore.UpdateChoiceTextDtoCore;
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

@Service
public class UpdateChoiceTextUseCase implements IUseCase<UpdateChoiceTextDtoCore, Choice> {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final IsOwnerUseCase isOwnerUseCase;
    private final SceneDao sceneDao;
    private final ChoiceDao choiceDao;

    public UpdateChoiceTextUseCase(
            IsOwnerUseCase isOwnerUseCase,
            GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
            SceneDao sceneDao,
            ChoiceDao choiceDao) {
        this.isOwnerUseCase = isOwnerUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.sceneDao = sceneDao;
        this.choiceDao = choiceDao;
    }

    public Choice handle(UpdateChoiceTextDtoCore updateChoiceTextDtoCore) {

        User user = getAuthenticatedUserUseCase.handle(null);

        Choice choice = choiceDao.findById(updateChoiceTextDtoCore.choiceId).orElseThrow(
                () -> new ChoiceNotFoundException("Choice: " + updateChoiceTextDtoCore.choiceId + " not found")
        );

        Scene scene = sceneDao.findById(choice.sceneId).orElseThrow(
                () -> new SceneNotFoundException("Scene: " + choice.sceneId + " not found")
        );

        OwningTestDtoCore owningTestDtoCore = new OwningTestDtoCore(
                scene.taleId,
                user.id
        );

        isOwnerUseCase.handle(owningTestDtoCore);

        choice.text = updateChoiceTextDtoCore.text;

        return choiceDao.save(choice);

    }

}
