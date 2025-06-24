package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.interfaces.daos.TaleDao;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetTaleByIdUseCase implements IUseCase<UUID, Tale> {

    private final TaleDao dao;

    public GetTaleByIdUseCase(TaleDao dao) {
        this.dao = dao;
    }

    public Tale handle(UUID taleId) {

        Optional<Tale> tale = dao.findById(taleId);

        return tale.orElse(null);

    }

}
