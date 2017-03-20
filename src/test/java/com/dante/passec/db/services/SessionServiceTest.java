package com.dante.passec.db.services;

import com.dante.passec.config.HibernateConfig;
import com.dante.passec.config.MainConfig;
import com.dante.passec.model.Session;
import com.dante.passec.model.UserRest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Calendar;
import java.util.List;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, MainConfig.class})
@Transactional
public class SessionServiceTest extends Assert{

    UserRest user;
    @Resource
    private EntityManagerFactory emf;
    private EntityManager em;
    @Resource
    private SessionService sessionService;
    @Resource
    private UserRestService userService;
    private Session session;

    @Before
    public void setUp(){
        em = emf.createEntityManager();
        user = new UserRest("login1","password1");
        userService.addUser(user);
    }
    @After
    public void drop(){
        session = null;
        List<Session> sessions = sessionService.allSessions();
        for (Session current : sessions) {
            sessionService.deleteSessionByToken(current.getToken());
        }
    }

    private Session firstSession(){
        List<Session> sessions = sessionService.allSessions();
        if(sessions.size()==0) return null;
        return sessions.get(0);
    }

    @Test
    public void addSessionShouldBeSuccessAddSession(){
        Session newSession = sessionService.addSession(user);
        Session session = firstSession();
        assertEquals(newSession, session);
    }
    @Test(expected = NullPointerException.class)
    public void addSessionShouldThrowNullPointerException(){
        sessionService.addSession(null);
    }
    @Test
    public void deleteSessionByTokenShouldBeSuccess(){
        Session session = sessionService.addSession(user);
        sessionService.deleteSessionByToken(session.getToken());
        Session firstSession = firstSession();
        assertNull(firstSession);
    }
    @Test
    public void sessionShouldBeNotActual(){
        Session session = sessionService.addSession(user);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(session.getDate());
        rightNow.add(Calendar.HOUR, 10);
        assertFalse(sessionService.sessionIsActual(session.getToken(), rightNow.getTime()));
    }
    @Test
    public void sessionShouldBeActual(){
        Session session = sessionService.addSession(user);
        UserRest userRest = sessionService.sessionIsActual(session.getToken());
        assertEquals(userRest, user);
    }
    @Test
    public void setNotIncludingShouldBeSuccess(){
        Session session = sessionService.addSession(user);
        sessionService.setNotIncluding(session);
        assertFalse(session.isIncluding());
    }



}
