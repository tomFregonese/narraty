package com.ynov.javaformation.narraty.irepositories;

import com.ynov.javaformation.narraty.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserDao {

    User save(User user);

    Optional<User> findById(UUID userId);

    void deleteById(UUID userId);



}
