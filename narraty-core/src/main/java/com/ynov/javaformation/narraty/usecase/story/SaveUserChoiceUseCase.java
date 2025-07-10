package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.dtosCore.SelectedChoiceDtoCore;
import com.ynov.javaformation.narraty.exceptions.story.*;
import com.ynov.javaformation.narraty.interfaces.daos.ChoiceDao;
import com.ynov.javaformation.narraty.interfaces.daos.SceneDao;
import com.ynov.javaformation.narraty.interfaces.daos.TaleDao;
import com.ynov.javaformation.narraty.interfaces.daos.UserTalePlayDao;
import com.ynov.javaformation.narraty.models.*;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import com.ynov.javaformation.narraty.usecase.auth.GetAuthenticatedUserUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SaveUserChoiceUseCase implements IUseCase<SelectedChoiceDtoCore, UUID> {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final TaleDao taleDao;
    private final SceneDao sceneDao;
    private final ChoiceDao choiceDao;
    private final UserTalePlayDao userTalePlayDao;

    public SaveUserChoiceUseCase(
            GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
            TaleDao taleDao,
            SceneDao sceneDao,
            ChoiceDao choiceDao,
            UserTalePlayDao userTalePlayDao) {
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.taleDao = taleDao;
        this.sceneDao = sceneDao;
        this.choiceDao = choiceDao;
        this.userTalePlayDao = userTalePlayDao;
    }

    public UUID handle(SelectedChoiceDtoCore selectedChoice) {

        User user = getAuthenticatedUserUseCase.handle(null);

        Choice choice = choiceDao.findById(selectedChoice.choiceId).orElseThrow(
                () -> new ChoiceNotFoundException("Choice not found with ID: " + selectedChoice.choiceId)
        );

        UserTalePlay utp = userTalePlayDao.findByUserAndTaleId(user.id, selectedChoice.taleId).orElseThrow(
                () -> new UserTalePlayNotFoundException("UserTalePlay not found")
        );

        if (!userAllowedToDoThisChoice(utp, choice)) throw new UserUnauthorizedToMakeThisChoiceException("User not allowed to do this choice");

        UUID idOfTheLastScene;

        utp.currentSceneId = choice.nextSceneId;

        Scene newScene = sceneDao.findById(utp.currentSceneId).orElseThrow(
                () -> new SceneNotFoundException("Scene not found with ID: " + utp.currentSceneId)
        );
        if (newScene.status == SceneStatus.Lose || newScene.status == SceneStatus.Win) {
            idOfTheLastScene = utp.currentSceneId;
            utp.currentSceneId = null;
            incrementPlaycount(utp);
        } else {
            idOfTheLastScene = null;
            userTalePlayDao.save(utp);
        }

        return idOfTheLastScene;

    }

    private boolean userAllowedToDoThisChoice(UserTalePlay utp, Choice choice) {
        return utp.currentSceneId.equals(choice.sceneId);
    }

    private void incrementPlaycount(UserTalePlay utp) {
        utp.playCount += 1;
        utp.currentSceneId = null;
        userTalePlayDao.save(utp);

        Tale tale = utp.tale;
        tale.playCount++;
        taleDao.save(tale);
    }

}
