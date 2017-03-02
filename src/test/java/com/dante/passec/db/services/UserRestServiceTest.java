package com.dante.passec.db.services;

import com.dante.passec.configs.HibernateConfigT;
import com.dante.passec.model.UserRest;
import com.dante.passec.utils.UserRestManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for userRestService
 */
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigT.class})
@Transactional
public class UserRestServiceTest extends Assert {
    @Resource
    private EntityManagerFactory emf;
    private EntityManager em;

    @Resource
    private UserRestService userService;

    List<UserRest> userRests;

    @Before
    public void setUp(){
        em = emf.createEntityManager();
        userRests = new ArrayList<>();
        userRests.add(UserRestManager.createUser("hello world", "worldes"));
    }
    @After
    public void drop(){

    }

    @Test
    public void testInsert(){
        userService.addUser(userRests.get(0));
        UserRest userRest = userService.userByLogin(userRests.get(0).getLogin());
        assertEquals(userRest.getLogin(), userRests.get(0).getLogin());
    }
    @Test(expected = JpaSystemException.class)
    public void testInsertWithNullLogin(){
        userRests.add(UserRestManager.createUser(null, "Hello null"));
        userService.addUser(userRests.get(1));
    }
    @Test(expected = JpaSystemException.class)
    public void testInsertWithNullPassword(){
        userRests.add(UserRestManager.createUser("Hello null pass", null));
        userService.addUser(userRests.get(1));
    }
    @Test(expected = JpaSystemException.class)
    public void testInsertTwoCopyLogin(){
        userRests.add(UserRestManager.createUser("hello world", "password"));
        userService.addUser(userRests.get(0));
        userService.addUser(userRests.get(1));
    }
    @Test
    public void getById(){
        userService.addUser(userRests.get(0));
        System.out.println(userService.userById(1L));
    }
    @Test
    public void getByLogin(){
        userService.addUser(userRests.get(0));
        System.out.println(userService.userByLogin("hello world"));
    }

}
