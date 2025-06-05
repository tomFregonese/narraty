package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.interfaces.daos.TaleDao;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import com.ynov.javaformation.narraty.usecase.auth.GetAuthenticatedUserUseCase;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GetAllUserTalesUseCase implements IUseCase<Void, Collection<Tale>> {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final TaleDao dao;

    public GetAllUserTalesUseCase(
            GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
            TaleDao dao) {
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.dao = dao;
    }

    /**
     * Retrieves all tales authored by the currently authenticated user (regardless of status).
     *
     * @param unused Unused parameter, can be null.
     * @return A Collection of Tale.
     */
    public Collection<Tale> handle(Void unused) {

        User user = getAuthenticatedUserUseCase.handle(null);

        return dao.findAllByAuthorId(user.id);

    }

}