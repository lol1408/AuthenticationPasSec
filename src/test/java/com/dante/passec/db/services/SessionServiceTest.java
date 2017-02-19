package com.dante.passec.db.services;

import com.dante.passec.configs.HibernateConfigT;
import com.dante.passec.model.Session;
import com.dante.passec.model.UserRest;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.dante.passec.utils.SessionManager.*;
import static com.dante.passec.utils.UserRestManager.*;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigT.class})
@Transactional
public class SessionServiceTest extends Assert{

    @Resource
    private EntityManagerFactory emf;
    private EntityManager em;

    @Resource
    private SessionService sessionService;
    @Resource
    private UserRestService userService;

    private List<Session> sessions;
    private List<UserRest> users;
    @Before
    public void setUp(){
        em = emf.createEntityManager();
        sessions = new ArrayList<>();
        UserRest user1 = createUser("login1","password1");
        UserRest user2 = createUser("login2", "password2");
        userService.addUser(user1);
        userService.addUser(user2);
        users = userService.allUsers();
        sessions = getSessionList(users);
    }

    @Test
    public void shouldBeSuccessAddSession(){
        sessionService.addSession(users.get(0));
        System.out.println(sessionService.findByToken(sessions.get(0).getToken()));
    }
    @Test
    public void sessionShouldBeActual(){
        Session session = sessionService.addSession(users.get(0));
        assertTrue(sessionService.sessionIsActual(session.getToken()));
    }
    @Test
    public void sessionShouldBeNotActual(){
        Session session = sessionService.addSession(users.get(0));
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(session.getDate());
        rightNow.add(Calendar.HOUR, 10);
        assertFalse(sessionService.sessionIsActual(session.getToken(), rightNow.getTime()));
    }
    @Test
    public void testData(){
        Calendar rightNow = Calendar.getInstance();
        Date d = new Date();
        System.out.println(d);
        rightNow.setTime(d);
        rightNow.add(Calendar.HOUR, 2);
        Date time = rightNow.getTime();
        System.out.println(d);
        assertTrue(time.getTime()>d.getTime());
    }


}
