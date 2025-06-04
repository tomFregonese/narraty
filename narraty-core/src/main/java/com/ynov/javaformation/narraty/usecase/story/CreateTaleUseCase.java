package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.interfaces.daos.TaleDao;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.TaleStatus;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CreateTaleUseCase implements IUseCase<Void, Tale> {

    private final TaleDao repository;

    public CreateTaleUseCase(TaleDao repository) {
        this.repository = repository;
    }

    public Tale handle(Void unused) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        Tale taleToSave = Tale.builder()
                .title("Title of the Tale")
                .authorId(user.id)
                .playCount(0)
                .status(TaleStatus.Draft)
                .build();

        return repository.save(taleToSave);

    }

}