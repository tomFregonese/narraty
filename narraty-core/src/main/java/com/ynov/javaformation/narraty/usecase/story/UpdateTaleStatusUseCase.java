package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.dtosCore.OwningTestDtoCore;
import com.ynov.javaformation.narraty.dtosCore.UpdateTaleStatusDtoCore;
import com.ynov.javaformation.narraty.exceptions.story.UnprocessableTaleException;
import com.ynov.javaformation.narraty.interfaces.daos.TaleDao;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.TaleErrors;
import com.ynov.javaformation.narraty.models.TaleStatus;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import com.ynov.javaformation.narraty.usecase.auth.GetAuthenticatedUserUseCase;
import com.ynov.javaformation.narraty.usecase.auth.IsOwnerUseCase;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UpdateTaleStatusUseCase implements IUseCase<UpdateTaleStatusDtoCore, Tale> {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final IsOwnerUseCase isOwnerUseCase;
    private final CheckTaleValidity checkTaleValidity;
    private final TaleDao dao;

    public UpdateTaleStatusUseCase(
            IsOwnerUseCase isOwnerUseCase,
            GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
            CheckTaleValidity checkTaleValidity,
            TaleDao dao) {
        this.isOwnerUseCase = isOwnerUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.checkTaleValidity = checkTaleValidity;
        this.dao = dao;
    }

    public Tale handle(UpdateTaleStatusDtoCore updateTaleStatusDtoCore) {

        User user = getAuthenticatedUserUseCase.handle(null);

        OwningTestDtoCore owningTestDtoCore = new OwningTestDtoCore(
                updateTaleStatusDtoCore.taleId,
                user.id
        );

        Tale tale = isOwnerUseCase.handle(owningTestDtoCore);

        if (updateTaleStatusDtoCore.status == TaleStatus.Published) {

            Collection<TaleErrors> taleErrors= checkTaleValidity.handle(tale);

            if (taleErrors != null) throw new UnprocessableTaleException("Cannot publish tale with errors", taleErrors);

        }

        tale.status = updateTaleStatusDtoCore.status;

        return dao.save(tale);

    }

}
