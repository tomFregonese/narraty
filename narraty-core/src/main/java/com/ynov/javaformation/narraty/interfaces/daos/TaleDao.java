package com.ynov.javaformation.narraty.interfaces.daos;

import com.ynov.javaformation.narraty.models.Tale;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaleDao {

    Tale save(Tale tale);

    Optional<Tale> findById(UUID id);

    Collection<Tale> findAllByAuthorId(UUID authorId);

    List<Tale> findAllPublished();

    void deleteById(UUID id);

    List<Tale> findAllByTitle(String title);

}
