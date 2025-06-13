package com.ynov.javaformation.narraty.validators.tale;

import com.ynov.javaformation.narraty.models.SceneStatus;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.TaleErrors;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AtLeastOneWinSceneValidator implements TaleValidator {

    public Optional<TaleErrors> validate(Tale tale) {
        boolean hasWin = tale.scenes.stream().anyMatch(s -> s.status == SceneStatus.Win);
        return hasWin ? Optional.empty() : Optional.of(TaleErrors.NoWinScene);
    }
}