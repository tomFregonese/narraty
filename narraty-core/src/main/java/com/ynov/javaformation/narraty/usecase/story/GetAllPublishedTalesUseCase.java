package com.ynov.javaformation.narraty.usecase.story;

import com.ynov.javaformation.narraty.interfaces.daos.TaleDao;
import com.ynov.javaformation.narraty.models.Tale;
import com.ynov.javaformation.narraty.usecase.IUseCase;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GetAllPublishedTalesUseCase implements IUseCase<Void, Collection<Tale>> {

    private final TaleDao dao;

    public GetAllPublishedTalesUseCase(TaleDao dao) {
        this.dao = dao;
    }

    public Collection<Tale> handle(Void unused) {

        return dao.findAllPublished();

    }

}
