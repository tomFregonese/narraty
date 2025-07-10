package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.dtosCore.SelectedChoiceDtoCore;
import com.ynov.javaformation.narraty.exceptions.story.SceneNotFoundException;
import com.ynov.javaformation.narraty.interfaces.daos.SceneDao;
import com.ynov.javaformation.narraty.models.Scene;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SaveUserChoiceAndFindNextSceneToDisplayUseCase implements IUseCase<SelectedChoiceDtoCore, Scene> {

    private final SaveUserChoiceUseCase saveUserChoiceUseCase;
    private final GetSceneToDisplayUseCase getSceneToDisplayUseCase;
    private final SceneDao sceneDao;

    public SaveUserChoiceAndFindNextSceneToDisplayUseCase(
            SaveUserChoiceUseCase saveUserChoiceUseCase,
            GetSceneToDisplayUseCase getSceneToDisplayUseCase,
            SceneDao sceneDao) {
        this.saveUserChoiceUseCase = saveUserChoiceUseCase;
        this.getSceneToDisplayUseCase = getSceneToDisplayUseCase;
        this.sceneDao = sceneDao;
    }

    public Scene handle(SelectedChoiceDtoCore selectedChoiceDtoCore) {

        UUID idOfTheLastScene = saveUserChoiceUseCase.handle(selectedChoiceDtoCore);
        if (idOfTheLastScene != null) {
            return sceneDao.findById(idOfTheLastScene).orElseThrow(
                    () -> new SceneNotFoundException("Scene not found")
            );
        }
        return getSceneToDisplayUseCase.handle(selectedChoiceDtoCore.taleId);

    }


}
