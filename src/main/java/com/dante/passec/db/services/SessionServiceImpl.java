package com.dante.passec.db.services;

import com.dante.passec.db.dao.SessionDao;
import com.dante.passec.exception.ForbiddenException;
import com.dante.passec.exception.UnauthorizedException;
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

    public Session findByToken(Integer token) {
        return dao.findByToken(token);
    }

    public Session addSession(UserRest user) {
        if(user.getActive()==false)
            throw new ForbiddenException();
        Session session = new Session(user);
        return dao.save(session);
    }

    public Session setNotIncluding(Session session) {
        session.setIncluding(false);
        return dao.saveAndFlush(session);
    }

    public UserRest sessionIsActual(Integer token) {
        long start = System.nanoTime();
        Session byToken = dao.findByToken(token);
        long end = System.nanoTime();
        System.out.println("is actual time: " + (end-start));
        Date date1 = new Date();
        if(byToken!=null && date1.getTime() < byToken.getDate().getTime() && byToken.isIncluding())
            return byToken.getUser();
        else throw new UnauthorizedException();
    }
    public boolean sessionIsActual(Integer token, Date currentDate){
        Session byToken = dao.findByToken(token);
        Date date1 = currentDate;
        if(date1.getTime() < byToken.getDate().getTime() && byToken.isIncluding()) return true;
        else throw new UnauthorizedException();
    }

    public void deleteSessionByToken(Integer token) {
        dao.deleteByToken(token);
    }
}
