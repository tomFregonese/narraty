package com.ynov.javaformation.narraty.models;

public enum TaleErrors {
    HasNotAtLeastFourScenes,
    NoStartScene,
    NoWinScene,
    NoLoseScene,
    InaccessibleScene,
    ChoicePointsToMissingScene,
    NoChoiceInNonEndingScene,
    ChoicesInEndingScene,
    ChoicePointsToSelf,
}
