package com.ynov.javaformation.narraty.interfaces.daos;

import com.ynov.javaformation.narraty.models.Tale;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaleDao {

    Tale save(Tale tale);

    Optional<Tale> findById(UUID id);

    List<Tale> findAll();

    void deleteById(UUID id);

    List<Tale> findAllByTitle(String title);

}
