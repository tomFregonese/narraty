package com.ynov.javaformation.narraty.interfaces.services;

import com.ynov.javaformation.narraty.models.User;

import java.util.UUID;

public interface ISessionService {

    /**
     * Saves a new session to the database and returns the token.
     *
     * @param user The user for whom the session is created.
     * @return UUID Token
     */
    UUID createSession(User user);

    /**
     * Checks if the token is valid.
     * @param token Takes a Bearer token as param
     * @return The User if token is valid, Null otherwise.
     */
    User validateToken(String token);

    /**
     * Deletes a session from the database.
     *
     * @param token The token of the session to delete.
     */
    void invalidateSession(UUID token);

    /**
     * Deletes a collection of sessions from the database.
     *
     * @param user The user to invalidate the sessions.
     * @param currentSessionId The session ID to keep active.
     */
    void invalidateUserSessionsExceptCurrent(User user, UUID currentSessionId);

}