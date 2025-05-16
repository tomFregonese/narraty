package com.ynov.javaformation.narraty.repositories;

import com.ynov.javaformation.narraty.entities.StoryEntity;
import com.ynov.javaformation.narraty.irepositories.StoryDao;
import com.ynov.javaformation.narraty.models.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class StoryJpaRepositoryAdapter implements StoryDao {

    @Autowired
    private StoryJpaRepository repository;

    public Story save(Story story) {
        StoryEntity storyEntity = StoryEntity.mapToEntity(story);
        return repository.save(storyEntity).mapToDomain();
    }

    public void delete(Story story) {
        StoryEntity storyEntity = StoryEntity.mapToEntity(story);
        repository.delete(storyEntity);
    }

    public Optional<Story> findById(UUID id) {
        Optional<StoryEntity> storyEntity = repository.findById(id);
        return storyEntity.map(StoryEntity::mapToDomain);
    }

    public List<Story> findAll() {
        List<StoryEntity> storiesEntity = repository.findAll();
        return storiesEntity.stream().map(StoryEntity::mapToDomain).toList();
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public List<Story> findAllByTitle(String title) {
        List<StoryEntity> storiesEntity = repository.findAllByTitle(title);
        return storiesEntity.stream().map(StoryEntity::mapToDomain).toList();
    }
}
