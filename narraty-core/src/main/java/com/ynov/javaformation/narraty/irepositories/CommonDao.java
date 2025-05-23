package com.ynov.javaformation.narraty.irepositories;

import java.util.List;
import java.util.Optional;

public interface CommonDao<T, ID> {

    T save(T session);

    T delete(T session);

    Optional<T> findById(ID id);

    Optional<List<T>> findAll();

    T deleteById(ID id);

}
