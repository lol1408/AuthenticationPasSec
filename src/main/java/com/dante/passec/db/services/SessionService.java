package com.dante.passec.db.services;

import com.dante.passec.model.Session;
import com.dante.passec.model.UserRest;

import java.util.Date;
import java.util.List;

/**
 * Service for Session Entity
 * @author Dante de Braso
 * @version 1.0
 */
public interface SessionService {

    Session findById(Long id);
    List<Session> allSessions();
    Session findByToken(Integer token);
    Session addSession(UserRest user);
    Session setNotIncluding(Session session);
    void deleteSession(Long id);
    UserRest sessionIsActual(Integer token);
    boolean sessionIsActual(Integer token, Date currentDate);
    void deleteSessionByToken(Integer token);

}
