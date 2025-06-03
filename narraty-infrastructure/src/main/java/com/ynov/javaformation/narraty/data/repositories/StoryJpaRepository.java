package com.ynov.javaformation.narraty.data.repositories;

import com.ynov.javaformation.narraty.data.entities.StoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StoryJpaRepository extends JpaRepository<StoryEntity, UUID> {

    List<StoryEntity> findAllByTitle(String title);

}
