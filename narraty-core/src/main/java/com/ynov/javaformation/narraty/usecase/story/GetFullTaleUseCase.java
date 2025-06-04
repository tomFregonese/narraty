package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.exceptions.story.TaleNotFoundException;
import com.ynov.javaformation.narraty.interfaces.daos.TaleDao;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetFullTaleUseCase implements IUseCase<UUID, Tale> {

    private final TaleDao repository;

    public GetFullTaleUseCase(TaleDao repository) {
        this.repository = repository;
    }

    public Tale handle(UUID id) {
        Optional<Tale> tale = repository.findById(id);
        if (tale.isEmpty()) throw new TaleNotFoundException("Tale with id: " + id + " not found");
        return tale.get();
    }

}
