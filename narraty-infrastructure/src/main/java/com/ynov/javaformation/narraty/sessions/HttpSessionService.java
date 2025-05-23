package com.ynov.javaformation.narraty.sessions;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Component
public class HttpSessionService implements ISessionService { // Come back to this implementation later

    private static final String USER_ID_KEY = "userId";

    private HttpSession getSession(boolean create) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw new IllegalStateException("No request context found");
        }
        return attrs.getRequest().getSession(create);
    }

    public void login(UUID userId) {
        getSession(true).setAttribute(USER_ID_KEY, userId); // On crée la session si elle n'existe pas
    }

    public void logout() {
        HttpSession session = getSession(false); // On ne crée pas de session si elle n'existe pas
        if (session != null) {
            session.invalidate();
        }
    }

    public UUID getCurrentUserId() {
        HttpSession session = getSession(false);
        if (session == null) {
            // Gérer le cas où il n'y a pas de session ou pas d'utilisateur loggué
            // Par exemple, retourner null ou lever une exception spécifique
            throw new IllegalStateException("No active session or user not logged in");
        }
        Object userId = session.getAttribute(USER_ID_KEY);
        if (userId == null) {
            // Gérer le cas où l'attribut n'est pas trouvé
            throw new IllegalStateException("User ID not found in session");
        }
        return (UUID) userId;
    }
}