package com.ynov.javaformation.narraty.data.repositories;

import com.ynov.javaformation.narraty.data.entities.TaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaleJpaRepository extends JpaRepository<TaleEntity, UUID> {

    @Query("SELECT t FROM TaleEntity t WHERE t.authorId = :authorId")
    List<TaleEntity> findAllByAuthorId(UUID authorId);

    @Query("SELECT t FROM TaleEntity t WHERE t.status = 'Published'")
    List<TaleEntity> findAllPublished();

    List<TaleEntity> findAllByTitle(String title);

}
