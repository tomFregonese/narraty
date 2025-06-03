package com.ynov.javaformation.narraty.interfaces.daos;

import com.ynov.javaformation.narraty.models.Story;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoryDao {

    Story save(Story story);

    void delete(Story story);

    Optional<Story> findById(UUID id);

    List<Story> findAll();

    void deleteById(UUID id);

    List<Story> findAllByTitle(String title);

}
