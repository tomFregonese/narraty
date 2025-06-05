package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.interfaces.daos.TaleDao;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.TaleStatus;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import com.ynov.javaformation.narraty.usecase.auth.GetAuthenticatedUserUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreateTaleUseCase implements IUseCase<Void, Tale> {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final TaleDao repository;

    public CreateTaleUseCase(GetAuthenticatedUserUseCase getAuthenticatedUserUseCase, TaleDao repository) {
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.repository = repository;
    }

    public Tale handle(Void unused) {

        User user = getAuthenticatedUserUseCase.handle(null);

        Tale taleToSave = Tale.builder()
                .title("Title of the Tale")
                .authorId(user.id)
                .playCount(0)
                .status(TaleStatus.Draft)
                .build();

        return repository.save(taleToSave);

    }

}