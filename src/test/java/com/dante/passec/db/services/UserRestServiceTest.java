package com.dante.passec.db.services;

import com.dante.passec.config.HibernateConfig;
import com.dante.passec.config.MainConfig;
import com.dante.passec.configs.HibernateConfigT;
import com.dante.passec.model.ResourceData;
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
@ContextConfiguration(classes = {HibernateConfig.class, MainConfig.class})
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
        userRests.add(UserRestManager.createUser("hello world olol", "worldasd"));

    }

    @After
    public void drop(){
        try {
            List<UserRest> allUsers = userService.allUsers();
            for(UserRest user: allUsers){
                userService.deleteUser(user.getId());
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        userRests = null;
    }

    @Test
    public void insertShouldBeSuccess(){
        userService.addUser(userRests.get(0));
        UserRest userRest = userService.userByLogin(userRests.get(0).getLogin());
        assertEquals(userRest.getLogin(), userRests.get(0).getLogin());
    }

    @Test(expected = JpaSystemException.class)
    public void insertShouldThrowJpaSystemException(){
        UserRest user = new UserRest(null, "password");
        userService.addUser(user);
    }

    @Test(expected = JpaSystemException.class)
    public void insertTwoSameLoginShouldThrowJpaSystemException(){
        UserRest user = new UserRest("login first", "password");
        UserRest second = new UserRest("login first", "password");
        userService.addUser(user);
        userService.addUser(second);
    }

    @Test
    public void getByIdShouldBeSuccess(){
        userService.addUser(userRests.get(0));
        System.out.println(userService.userById(1L));
    }

    @Test
    public void getByLoginShouldBeSuccess(){
        userService.addUser(userRests.get(0));
        System.out.println(userService.userByLogin("hello world"));
    }

}
