package com.ynov.javaformation.narraty.interfaces.daos;

import com.ynov.javaformation.narraty.models.Scene;

import java.util.Optional;
import java.util.UUID;

public interface SceneDao {

    Scene save(Scene scene);

    Optional<Scene> findById(UUID id);

    Optional<Scene> findFirstSceneByTaleId(UUID taleId);

    void deleteById(UUID id);

}
