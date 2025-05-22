package com.ynov.javaformation.narraty.irepositories;

import java.util.List;
import java.util.Optional;

public interface CommonDao<T, ID> {

    T save(T entity);

    T delete(T entity);

    Optional<T> findById(ID id);

    Optional<List<T>> findAll();

    T deleteById(ID id);

}
