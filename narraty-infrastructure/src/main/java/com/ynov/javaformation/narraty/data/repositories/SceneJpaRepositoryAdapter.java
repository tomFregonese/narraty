package com.ynov.javaformation.narraty.data.repositories;

import com.ynov.javaformation.narraty.data.entities.SceneEntity;
import com.ynov.javaformation.narraty.interfaces.daos.SceneDao;
import com.ynov.javaformation.narraty.models.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SceneJpaRepositoryAdapter implements SceneDao {

    @Autowired
    private SceneJpaRepository repository;

    public Scene save(Scene scene) {
        SceneEntity sceneEntity = SceneEntity.mapToEntity(scene);
        return repository.save(sceneEntity).mapToDomain();
    }

    public Optional<Scene> findById(UUID id) {
        Optional<SceneEntity> sceneEntity = repository.findById(id);
        return sceneEntity.map(SceneEntity::mapToDomain);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

}
