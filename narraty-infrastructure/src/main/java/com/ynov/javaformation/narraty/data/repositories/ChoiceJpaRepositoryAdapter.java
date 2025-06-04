package com.ynov.javaformation.narraty.data.repositories;

import com.ynov.javaformation.narraty.data.entities.ChoiceEntity;
import com.ynov.javaformation.narraty.interfaces.daos.ChoiceDao;
import com.ynov.javaformation.narraty.models.Choice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ChoiceJpaRepositoryAdapter implements ChoiceDao {

    @Autowired
    private ChoiceJpaRepository repository;

    public Choice save(Choice choice) {
        ChoiceEntity choiceEntity = ChoiceEntity.mapToEntity(choice);
        return repository.save(choiceEntity).mapToDomain();
    }

    public Optional<Choice> findById(UUID id) {
        Optional<ChoiceEntity> choiceEntity = repository.findById(id);
        return choiceEntity.map(ChoiceEntity::mapToDomain);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

}
