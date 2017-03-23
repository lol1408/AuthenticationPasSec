package com.dante.passec.db.services;

import com.dante.passec.config.HibernateConfig;
import com.dante.passec.config.MainConfig;
import com.dante.passec.model.UserRest;
import com.dante.passec.utils.UserRestManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
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
    List<UserRest> users;
    @Resource
    private EntityManagerFactory emf;
    private EntityManager em;
    @Resource
    private UserRestService userService;

    @Before
    public void setUp(){
        em = emf.createEntityManager();
        users = new ArrayList<>();
        users.add(UserRestManager.createUser("hello world", "worldes"));
        users.add(UserRestManager.createUser("hello world olol", "worldasd"));

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
        }
        users = null;
    }
    private UserRest firstUserRest(){
        List<UserRest> allUsers = userService.allUsers();
        if(allUsers.size()==0) return null;
        else return allUsers.get(0);
    }

    @Test
    public void insertShouldBeSuccess(){
        userService.addUser(users.get(0));
        UserRest userRest = firstUserRest();
        assertEquals(userRest.getLogin(), users.get(0).getLogin());
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
        UserRest userRest = userService.addUser(users.get(0));
        UserRest byId = userService.userById(userRest.getId());
        assertEquals(userRest, byId);
    }
    @Test
    public void getByIdShouldReturnNull(){
        UserRest userRest = userService.userById(1L);
        assertNull(userRest);
    }

    @Test
    public void getByLoginShouldBeSuccess(){
        UserRest userRest = userService.addUser(users.get(0));
        UserRest byLogin = userService.userByLogin(userRest.getLogin());
        assertEquals(userRest, byLogin);
    }
    @Test
    public void getAllShouldBeSuccess(){
        for (UserRest userRest : users) {
            userService.addUser(userRest);
        }
        List<UserRest> allUsers = userService.allUsers();
        for (int i=0; i<allUsers.size(); i++) {
            assertEquals(allUsers.get(i), users.get(i));
        }
    }
    @Test
    public void updateUserShouldBeSuccess(){
        UserRest user = users.get(0);
        userService.addUser(user);
        UserRest update = new UserRest(user);
        update.setLogin("new login");
        update.setPassword("new password");
        userService.updateUser(update);
        UserRest firstRecord = firstUserRest();
        /*Первоначальная запись не должна совпадать с первой
        записью в базе, кроме id         */
        assertEquals(user.getId(), firstRecord.getId());
        assertNotEquals(user.getLogin(), firstRecord.getLogin());
        assertNotEquals(user.getPassword(), firstRecord.getPassword());

        /*Обновленная переменная должна полностью
        совпадать с первой записью в базе*/
        assertEquals(update, firstRecord);
    }
    @Test(expected = JpaSystemException.class)
    public void updateUserShouldThrowJpaSystemException(){
        UserRest user = users.get(0);
        userService.addUser(user);
        UserRest update = new UserRest(user);
        update.setId(0L);
        userService.updateUser(update);
    }
    @Test
    public void deleteUserShouldBeSuccess(){
        UserRest user = users.get(0);
        userService.addUser(user);
        userService.deleteUser(user.getId());
        UserRest userRest = firstUserRest();
        assertNull(userRest);
    }
    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteUserShouldThrowEmptyResultDataAccessException(){
        userService.deleteUser(1L);
    }
    @Test
    public void userIsRealShouldReturnTrue(){
        UserRest user = users.get(0);
        userService.addUser(user);
        assertTrue(userService.userIsReal(user.getLogin(),user.getPassword()));
    }
    @Test
    public void userIsRealShouldReturnFalse(){
        UserRest user = users.get(0);
        userService.addUser(user);
        assertFalse(userService.userIsReal("login", "password"));
    }
    @Test
    public void checkAlreadyExistShouldReturnTrue(){
        UserRest user = users.get(0);
        userService.addUser(user);
        assertTrue(userService.checkAlreadyExist(user.getLogin()));
    }
    @Test
    public void checkAlreadyExistShouldReturnFalse(){
        assertFalse(userService.checkAlreadyExist("login"));
    }

}
