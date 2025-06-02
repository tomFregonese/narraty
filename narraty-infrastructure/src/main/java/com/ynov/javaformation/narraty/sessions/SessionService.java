package com.ynov.javaformation.narraty.sessions;

import com.ynov.javaformation.narraty.irepositories.SessionDao;
import com.ynov.javaformation.narraty.irepositories.UserDao;
import com.ynov.javaformation.narraty.models.Session;
import com.ynov.javaformation.narraty.models.User;
import com.ynov.javaformation.narraty.validators.SessionValidator;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SessionService implements ISessionService {

    private final SessionDao sessionDao; // Can I use autowired here?
    private final UserDao userDao;

    public SessionService(SessionDao sessionDao, UserDao userDao) {
        this.sessionDao = sessionDao;
        this.userDao = userDao;
    }

    public UUID createSession(User user) {

        Session session = new Session(
                null,
                user.id,
                null
        );

        Session savedSession = sessionDao.save(session);

        return savedSession.token;

    }

    public User validateToken(String token) {
        try {
            UUID parsedToken = UUID.fromString(token);
            Session session = sessionDao.findByToken(parsedToken).orElse(null);
            if (SessionValidator.isValid(session)) {
                return userDao.findById(session.userId).orElse(null);
            } else {
                return null;
            }
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public void deleteSession(UUID token) {
        sessionDao.delete(token);
    }

    public void deleteAllSessionsOfAUser(UUID userId) {
        sessionDao.deleteAllByUserId(userId);
    }

}