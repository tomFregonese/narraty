package com.ynov.javaformation.narraty.models;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Builder
public class UserTalePlay {

    public UUID id;

    public User user;

    public Tale tale;

    /**
     * The number of times the user has finished the tale.
     */
    public int playCount;

    public UUID currentSceneId;

    public LocalDateTime LastPlayedAt;


    public boolean isUserReadingThisStory() {
        return currentSceneId != null;
    }

}
