package com.dante.passec.db.services;

import com.dante.passec.model.Session;
import com.dante.passec.model.UserRest;

/**
 * Service for Session Entity
 * @author Dante de Braso
 * @version 1.0
 */
public interface SessionService {

    Session findById(Long id);
    Session findByToken(String token);
    Session addSession(Session session);
    Session updateSession(Session session);
    void deleteSession(Long id);

}
