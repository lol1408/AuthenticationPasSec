package com.dante.passec.db.services;

import com.dante.passec.db.dao.SessionDao;
import com.dante.passec.model.Session;
import com.dante.passec.model.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;


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

    public Session findByToken(Integer token) {
        return dao.findByToken(token);
    }

    public Session addSession(UserRest user) {
        Session session = new Session(user);
        return dao.save(session);
    }

    public Session updateSession(Session session) {
        return dao.saveAndFlush(session);
    }

    public void deleteSession(Long id) {
        dao.delete(id);
    }

    public boolean sessionIsActual(Integer token) {
        Session byToken = dao.findByToken(token);
        Date date1 = new Date();
        return date1.getTime() < byToken.getDate().getTime();
    }
    public boolean sessionIsActual(Integer token, Date currentDate){
        Session byToken = dao.findByToken(token);
        Date date1 = currentDate;
        return date1.getTime() < byToken.getDate().getTime();
    }
}
