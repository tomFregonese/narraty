package com.ynov.javaformation.narraty.sessions;

import java.util.UUID;

public interface ISessionService {

    /**
     * Logs in the user with the specified user ID.
     * <p>
     * This action should establish a persistent session for the user, allowing
     * subsequent calls to {@link #getCurrentUserId()} to identify them.
     * </p>
     *
     * @param userId The unique identifier of the user to log in. Must not be null.
     * @throws IllegalArgumentException if userId is null.
     * @throws IllegalStateException if the login operation cannot be performed due to
     * an underlying system issue (e.g., session mechanism unavailable).
     */
    void login(UUID userId);

    /**
     * Logs out the currently authenticated user.
     * <p>
     * This action should terminate the user's current session, effectively
     * removing their authenticated state from the application.
     * </p>
     *
     * @throws IllegalStateException if the logout operation cannot be performed (e.g.,
     * no active session to terminate or an issue with the underlying session mechanism).
     */
    void logout();

    /**
     * Retrieves the unique identifier of the currently authenticated user.
     *
     * @return The {@link UUID} of the currently logged-in user.
     * @throws IllegalStateException if no user is currently authenticated or if the
     * user's identifier cannot be retrieved (e.g., no active session, or session
     * does not contain valid user information).
     */
    UUID getCurrentUserId();

}