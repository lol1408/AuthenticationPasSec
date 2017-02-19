package com.dante.passec.db.services;

import com.dante.passec.db.dao.SessionDao;
import com.dante.passec.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SessionServiceImpl with JpaRepository.
 * @author Dante de Braso
 * @version 1.0
 */
@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    SessionDao dao;

    public Session findById(Long id) {
        return dao.findOne(id);
    }

    public Session findByToken(String token) {
        return dao.findByToken(token);
    }

    public Session addSession(Session session) {
        return dao.save(session);
    }

    public Session updateSession(Session session) {
        return dao.saveAndFlush(session);
    }

    public void deleteSession(Long id) {
        dao.delete(id);
    }
}
