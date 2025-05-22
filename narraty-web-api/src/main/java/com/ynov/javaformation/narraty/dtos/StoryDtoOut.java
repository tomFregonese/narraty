package com.ynov.javaformation.narraty.dtos;

import com.ynov.javaformation.narraty.models.Story;
import lombok.Data;

import java.util.Date;

@Data
public class StoryDtoOut {

    String ttl;
    String dsc;
    Date crtAt;

    public StoryDtoOut(Story story) {
        this.ttl = story.title;
        this.dsc = story.description;
        this.crtAt = story.createdAt;
    }

}