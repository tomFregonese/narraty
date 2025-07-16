package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.dtosCore.OwningTestDtoCore;
import com.ynov.javaformation.narraty.dtosCore.UpdateTaleTitleDtoCore;
import com.ynov.javaformation.narraty.interfaces.daos.TaleDao;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import com.ynov.javaformation.narraty.usecase.auth.GetAuthenticatedUserUseCase;
import com.ynov.javaformation.narraty.usecase.auth.IsOwnerUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateTaleTitleUseCase implements IUseCase<UpdateTaleTitleDtoCore, Tale> {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final IsOwnerUseCase isOwnerUseCase;
    private final TaleDao dao;

    public UpdateTaleTitleUseCase(
            IsOwnerUseCase isOwnerUseCase,
            GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
            TaleDao dao) {
        this.isOwnerUseCase = isOwnerUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.dao = dao;
    }

    public Tale handle(UpdateTaleTitleDtoCore updateTaleTitleDtoCore) {

        User user = getAuthenticatedUserUseCase.handle(null);

        OwningTestDtoCore owningTestDtoCore = new OwningTestDtoCore(
                updateTaleTitleDtoCore.taleId,
                user.id
        );

        Tale tale = isOwnerUseCase.handle(owningTestDtoCore);

        tale.title = updateTaleTitleDtoCore.title;
        tale.updatedAt = LocalDateTime.now();

        return dao.save(tale);

    }

}
