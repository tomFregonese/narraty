package com.ynov.javaformation.narraty.validators.auth;

import com.ynov.javaformation.narraty.models.Session;

import java.time.LocalDateTime;

public class SessionValidator {

    private static final long SESSION_EXPIRATION_DAYS = 7;

    /**
     * Validates if a session token is still active based on its expiration date.
     *
     * @param session The session object to validate.
     * @return true if the session is still valid, false otherwise.
     */
    public static boolean isValid(Session session) {
        if (session == null) {
            return false;
        }
        return session.createdAt.plusDays(SESSION_EXPIRATION_DAYS).isAfter(LocalDateTime.now());
    }

    public static long getSessionExpirationDays() {
        return SESSION_EXPIRATION_DAYS;
    }

}