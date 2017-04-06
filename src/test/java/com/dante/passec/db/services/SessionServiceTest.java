package com.dante.passec.db.services;

import com.dante.passec.exception.ForbiddenException;
import com.dante.passec.exception.UnauthorizedException;
import com.dante.passec.model.Session;
import com.dante.passec.model.UserRest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
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
    ArrayList<Integer> tokens = new ArrayList<>();

    @Before
    public void setUp(){
        em = emf.createEntityManager();
        user = new UserRest("login1","password1","sergey.king96@mail.ru",true);
        userService.addUser(user);
    }
    @After
    public void drop(){
        session = null;
        for (Integer token : tokens) {
            sessionService.deleteSessionByToken(token);
        }
        tokens = null;
    }

    @Test
    public void addSessionShouldBeSuccessAddSession(){
        Session newSession = sessionService.addSession(user);
        Integer token = newSession.getToken();
        tokens.add(token);
        Session session = sessionService.findByToken(token);
        assertEquals(newSession, session);
    }
    @Test(expected = ForbiddenException.class)
    public void addSessionShouldThrowForbiddenExceptin(){
        UserRest user = new UserRest("login1", "password", "sergey.king96@mail.ru", false);
        sessionService.addSession(user);
    }

    @Test(expected = NullPointerException.class)
    public void addSessionShouldThrowNullPointerException(){
        sessionService.addSession(null);
    }

    @Test
    public void deleteSessionByTokenShouldBeSuccess(){
        Session session = sessionService.addSession(user);
        Integer token = session.getToken();
        sessionService.deleteSessionByToken(token);
        Session firstSession = sessionService.findByToken(token);
        assertNull(firstSession);
    }
    @Test(expected = UnauthorizedException.class)
    public void sessionShouldBeThrowUnauthorizedException(){
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
