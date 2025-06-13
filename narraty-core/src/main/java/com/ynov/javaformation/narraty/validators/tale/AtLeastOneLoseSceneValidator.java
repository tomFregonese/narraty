package com.ynov.javaformation.narraty.validators.tale;

import com.ynov.javaformation.narraty.models.SceneStatus;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.TaleErrors;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AtLeastOneLoseSceneValidator implements TaleValidator {

    public Optional<TaleErrors> validate(Tale tale) {
        boolean hasLose = tale.scenes.stream().anyMatch(s -> s.status == SceneStatus.Lose);
        return hasLose ? Optional.empty() : Optional.of(TaleErrors.NoLoseScene);
    }
}