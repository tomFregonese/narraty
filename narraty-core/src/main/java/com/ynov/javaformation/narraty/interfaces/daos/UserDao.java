package com.ynov.javaformation.narraty.interfaces.daos;

import com.ynov.javaformation.narraty.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserDao {

    User save(User user);

    Optional<User> findById(UUID userId);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    void deleteById(UUID userId);



}
