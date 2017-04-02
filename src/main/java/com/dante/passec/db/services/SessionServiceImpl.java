package com.dante.passec.db.services;

import com.dante.passec.db.dao.SessionDao;
import com.dante.passec.exception.LockedException;
import com.dante.passec.model.Session;
import com.dante.passec.model.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


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
        if(user.getActive()==false)
            return null;
        Session session = new Session(user);
        return dao.save(session);
    }

    public Session setNotIncluding(Session session) {
        session.setIncluding(false);
        return dao.saveAndFlush(session);
    }
    public List<Session> allSessions(){
        return dao.findAll();
    }
    public void deleteSession(Long id) {
        dao.delete(id);
    }

    public UserRest sessionIsActual(Integer token) {
        long start = System.nanoTime();
        Session byToken = dao.findByToken(token);
        long end = System.nanoTime();
        System.out.println("is actual time: " + (end-start));
        Date date1 = new Date();
        if(byToken!=null && date1.getTime() < byToken.getDate().getTime() && byToken.isIncluding())
            return byToken.getUser();
        else return null;
    }
    public boolean sessionIsActual(Integer token, Date currentDate){
        Session byToken = dao.findByToken(token);
        Date date1 = currentDate;
        return date1.getTime() < byToken.getDate().getTime() && byToken.isIncluding();
    }

    public void deleteSessionByToken(Integer token) {
        dao.deleteByToken(token);
    }
}
