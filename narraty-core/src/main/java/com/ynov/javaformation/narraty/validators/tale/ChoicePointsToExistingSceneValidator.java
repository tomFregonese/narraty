package com.ynov.javaformation.narraty.validators.tale;

import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.TaleErrors;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ChoicePointsToExistingSceneValidator implements TaleValidator {

    public Optional<TaleErrors> validate(Tale tale) {
        Set<UUID> sceneIds = tale.scenes.stream().map(s -> s.id).collect(Collectors.toSet());
        boolean allValid = tale.scenes.stream()
                .flatMap(s -> s.choices.stream())
                .allMatch(c -> sceneIds.contains(c.nextSceneId));
        return allValid ? Optional.empty() : Optional.of(TaleErrors.ChoicePointsToMissingScene);
    }
}