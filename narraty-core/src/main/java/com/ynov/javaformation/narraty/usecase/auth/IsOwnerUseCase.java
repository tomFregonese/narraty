package com.ynov.javaformation.narraty.usecase.auth;

import com.ynov.javaformation.narraty.dtosCore.OwningTestDtoCore;
import com.ynov.javaformation.narraty.exceptions.story.NotTaleAuthorException;
import com.ynov.javaformation.narraty.exceptions.story.TaleNotFoundException;
import com.ynov.javaformation.narraty.interfaces.daos.TaleDao;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import org.springframework.stereotype.Service;

@Service
public class IsOwnerUseCase implements IUseCase<OwningTestDtoCore, Tale> {

    private final TaleDao dao;

    public IsOwnerUseCase(TaleDao dao) {
        this.dao = dao;
    }

    /**
     * Checks if the user doing the request is the owner of the tale.
     *
     * @param credentials containing the ID of the user making the request and the ID of the tale to check ownership for
     * @return The tale if the user is the owner, throws an NotTaleAuthorException otherwise
     */
    public Tale handle(OwningTestDtoCore credentials) {

        Tale tale = dao.findById(credentials.taleId)
                .orElseThrow(() -> new TaleNotFoundException("Tale not found with id: " + credentials.taleId));

        if (!tale.authorId.equals(credentials.userId)) throw new NotTaleAuthorException("User: " + credentials.userId +
                " is not the author of tale: " + credentials.taleId);

        return tale;

    }

}
