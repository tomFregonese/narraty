package com.ynov.javaformation.narraty.models;

import lombok.Getter;

@Getter
public enum TaleErrors {

    HasNotAtLeastFourScenes("The tale must have at least four scenes."),

    NoStartScene("The tale must have a start scene and only one."),

    NoWinScene("The tale must have a win scene."),

    NoLoseScene("The tale must have a lose scene."),

    InaccessibleScene("A scene is not accessible from the start scene."),

    ChoicePointsToMissingScene("A choice points to a scene that do not exist."),

    NoChoiceInNonEndingScene("A non-ending scene must have at least one choice."),

    ChoicesInEndingScene("An ending scene must not have any choices."),

    ChoicePointsToSelf("A choice cannot point to the same scene.");


    public final String message;

    TaleErrors(String message) {
        this.message = message;
    }

}
