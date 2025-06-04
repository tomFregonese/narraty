package com.ynov.javaformation.narraty.interfaces.daos;

import com.ynov.javaformation.narraty.models.Choice;

import java.util.Optional;
import java.util.UUID;

public interface ChoiceDao {

    Choice save(Choice choice);

    Optional<Choice> findById(UUID id);

    void deleteById(UUID id);

}
