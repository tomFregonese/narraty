package com.ynov.javaformation.narraty.data.repositories;

import com.ynov.javaformation.narraty.data.entities.TaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaleJpaRepository extends JpaRepository<TaleEntity, UUID> {

    List<TaleEntity> findAllByTitle(String title);

}
