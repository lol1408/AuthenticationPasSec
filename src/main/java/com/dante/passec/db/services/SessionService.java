package com.dante.passec.db.services;

import com.dante.passec.model.Session;
import com.dante.passec.model.UserRest;

import java.util.Date;

/**
 * Service for Session Entity
 * @author Dante de Braso
 * @version 1.0
 */
public interface SessionService {

    Session findById(Long id);
    Session findByToken(Integer token);
    Session addSession(UserRest user);
    Session updateSession(Session session);
    void deleteSession(Long id);
    boolean sessionIsActual(Integer token);
    boolean sessionIsActual(Integer token, Date currentDate);
    void deleteSessionByToken(Integer token);

}
