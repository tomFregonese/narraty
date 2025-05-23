package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.irepositories.StoryDao;
import com.ynov.javaformation.narraty.models.Story;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SaveStoryUseCase implements IUseCase<Story, Story> {

    private final StoryDao repository;

    public SaveStoryUseCase(StoryDao repository) {
        this.repository = repository;
    }

    public Story handle(Story story) throws Exception {

        try {

            story.createdAt = new Date();

            // TODO Add a domain validator here

            return repository.save(story);

        } catch (Exception e) {
            throw new Exception("Error saving story: " + e.getMessage());
        }

    }

}