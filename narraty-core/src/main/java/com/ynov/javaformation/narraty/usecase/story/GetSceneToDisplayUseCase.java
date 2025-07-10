package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.exceptions.story.SceneNotFoundException;
import com.ynov.javaformation.narraty.exceptions.story.TaleNotFoundException;
import com.ynov.javaformation.narraty.interfaces.daos.SceneDao;
import com.ynov.javaformation.narraty.interfaces.daos.TaleDao;
import com.ynov.javaformation.narraty.interfaces.daos.UserTalePlayDao;
import com.ynov.javaformation.narraty.models.Scene;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.models.UserTalePlay;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import com.ynov.javaformation.narraty.usecase.auth.GetAuthenticatedUserUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class GetSceneToDisplayUseCase implements IUseCase<UUID, Scene> {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final TaleDao taleDao;
    private final SceneDao sceneDao;
    private final UserTalePlayDao userTalePlayDao;

    public GetSceneToDisplayUseCase(
            GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
            TaleDao taleDao,
            SceneDao sceneDao,
            UserTalePlayDao userTalePlayDao) {
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.taleDao = taleDao;
        this.sceneDao = sceneDao;
        this.userTalePlayDao = userTalePlayDao;
    }

    public Scene handle(UUID taleId) {

        User user = getAuthenticatedUserUseCase.handle(null);

        Optional<UserTalePlay> utpOpt = userTalePlayDao.findByUserAndTaleId(user.id, taleId);

        UserTalePlay utp = utpOpt.orElse(null);


        if (utp == null || !utp.isUserReadingThisStory()) {
            Scene startScene = sceneDao.findFirstSceneByTaleId(taleId).orElseThrow(
                    () -> new SceneNotFoundException("No start scene found for tale: " + taleId)
            );

            if (utp == null) {
                utp = UserTalePlay.builder()
                        .user(user)
                        .tale(taleDao.findById(taleId).orElseThrow(
                                () -> new TaleNotFoundException("Tale not found for tale: " + taleId)
                        ))
                        .playCount(0)
                        .build();
            }

            utp.currentSceneId = startScene.id;
            updateUTPLastPlayed(utp);
            return startScene;

        }

        Scene scene = sceneDao.findById(utp.currentSceneId).orElseThrow(
                () -> new SceneNotFoundException("Scene not found")
        );
        updateUTPLastPlayed(utp);
        return scene;

    }

    private void updateUTPLastPlayed(UserTalePlay utp) {
        utp.LastPlayedAt = LocalDateTime.now();
        userTalePlayDao.save(utp);
    }

}
