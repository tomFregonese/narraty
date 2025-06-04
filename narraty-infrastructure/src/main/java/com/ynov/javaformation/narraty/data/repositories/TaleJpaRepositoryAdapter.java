package com.ynov.javaformation.narraty.data.repositories;

import com.ynov.javaformation.narraty.data.entities.TaleEntity;
import com.ynov.javaformation.narraty.interfaces.daos.TaleDao;
import com.ynov.javaformation.narraty.models.Tale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TaleJpaRepositoryAdapter implements TaleDao {

    @Autowired
    private TaleJpaRepository repository;

    public Tale save(Tale tale) {
        TaleEntity taleEntity = TaleEntity.mapToEntity(tale);
        return repository.save(taleEntity).mapToDomain();
    }

    public Optional<Tale> findById(UUID id) {
        Optional<TaleEntity> taleEntity = repository.findById(id);
        return taleEntity.map(TaleEntity::mapToDomain);
    }

    public List<Tale> findAll() {
        List<TaleEntity> talesEntity = repository.findAll();
        return talesEntity.stream().map(TaleEntity::mapToDomain).toList();
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public List<Tale> findAllByTitle(String title) {
        List<TaleEntity> talesEntity = repository.findAllByTitle(title);
        return talesEntity.stream().map(TaleEntity::mapToDomain).toList();
    }
}
