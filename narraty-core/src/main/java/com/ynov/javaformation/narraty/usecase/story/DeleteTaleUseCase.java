package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.dtosCore.OwningTestDtoCore;
import com.ynov.javaformation.narraty.interfaces.daos.TaleDao;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import com.ynov.javaformation.narraty.usecase.auth.GetAuthenticatedUserUseCase;
import com.ynov.javaformation.narraty.usecase.auth.IsOwnerUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteTaleUseCase implements IUseCase<UUID, Void> {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final IsOwnerUseCase isOwnerUseCase;
    private final TaleDao dao;

    public DeleteTaleUseCase(
            GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
            IsOwnerUseCase isOwnerUseCase,
            TaleDao dao) {
        this.isOwnerUseCase = isOwnerUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.dao = dao;
    }

    public Void handle(UUID taleId) {

        User user = getAuthenticatedUserUseCase.handle(null);

        OwningTestDtoCore owningTestDtoCore = new OwningTestDtoCore(
                taleId,
                user.id
        );

        Tale tale = isOwnerUseCase.handle(owningTestDtoCore);

        dao.deleteById(tale.id);

        return null;

    }

}
