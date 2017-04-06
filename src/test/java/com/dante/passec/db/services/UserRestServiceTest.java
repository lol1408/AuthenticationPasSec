package com.dante.passec.db.services;

import com.dante.passec.exception.EmailIsBusyException;
import com.dante.passec.exception.ForbiddenException;
import com.dante.passec.exception.LoginIsBusyException;
import com.dante.passec.model.UserRest;
import org.apache.log4j.Logger;
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
import java.util.List;

/**
 * Tests for userRestService
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserRestServiceTest extends Assert {
    List<UserRest> users;
    private static final Logger logger = Logger.getLogger(UserRestServiceTest.class);
    @Resource
    private EntityManagerFactory emf;
    private EntityManager em;
    @Resource
    private UserRestService userService;

    @Before
    public void setUp(){
        em = emf.createEntityManager();
        users = new ArrayList<>();
        users.add(new UserRest("hello world", "worldes", "mail", true));
        users.add(new UserRest("hello world olol", "worldasd", "mail2", true));
    }
    @After
    public void drop(){
        for (int i = 0; i < users.size(); i++) {
            try {
                userService.deleteUserByLogin(users.get(i).getLogin());
            }catch (Exception ex){
                logger.info("oops");
            }
        }
        users = null;
    }

    @Test
    public void insertShouldBeSuccess(){
        UserRest addedUser = userService.addUser(users.get(0));
        UserRest userRest = userService.userByLoginOrMail(addedUser.getLogin(), null);
        assertEquals(userRest.getLogin(), users.get(0).getLogin());
    }
    @Test
    public void selectByLogAndMailWhereMailIsNull(){
        UserRest addedUser = userService.addUser(users.get(0));
        UserRest userRest = userService.userByLoginOrMail("hello world", null);
        assertEquals(addedUser, userRest);
    }
    @Test
    public void selectByLogAndMailWhereMailIsFail(){
        UserRest addedUser = userService.addUser(users.get(0));
        UserRest userRest = userService.userByLoginOrMail("hello world", "FAIL");
        assertEquals(userRest, addedUser);
    }
    @Test
    public void selectByLogAndMailWhereLoginIsNull(){
        UserRest addedUser = userService.addUser(users.get(0));
        UserRest userRest = userService.userByLoginOrMail(null, "mail");
        assertEquals(userRest, addedUser);
    }
    @Test
    public void selectByLogAndMailShouldReturnNull(){
        userService.addUser(users.get(0));
        UserRest userRest = userService.userByLoginOrMail("asd", "qwe");
        assertNull(userRest);
    }
    @Test(expected = ForbiddenException.class)
    public void insertShouldThrowJpaSystemException(){
        UserRest user = new UserRest(null, "password", "sergey.king96@mail.ru", true);
        userService.addUser(user);
    }

    @Test(expected = ForbiddenException.class)
    public void insertTwoSameLoginShouldThrowJpaSystemException(){
        UserRest second = new UserRest(users.get(0));
        userService.addUser(users.get(0));
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
    public void getByMailShouldBeSuccess(){
        UserRest userRest = userService.addUser(users.get(0));
        UserRest byMail = userService.userByMail(userRest.getMail());
        assertEquals(userRest, byMail);
    }
    @Test
    public void updateUserShouldBeSuccess(){
        UserRest addedUser = userService.addUser(users.get(0));
        addedUser.setLogin("new login");
        addedUser.setMail("new mail");
        userService.updateUser(addedUser);

        assertNull(userService.userByLoginOrMail(users.get(0).getLogin(), users.get(0).getMail()));
        assertEquals(userService.userByLoginOrMail(addedUser.getLogin(), ""), addedUser);
    }
    @Test(expected = ForbiddenException.class)
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
        UserRest addedUser = userService.addUser(user);
        userService.deleteUser(user.getId());
        UserRest userRest = userService.userByLoginOrMail(addedUser.getLogin(), addedUser.getMail());
        assertNull(userRest);
    }

    @Test(expected = ForbiddenException.class)
    public void deleteUserShouldThrowEmptyResultDataAccessException(){
        userService.deleteUser(1L);
    }

    @Test
    public void userIsRealShouldReturnTrue(){
        UserRest user = users.get(0);
        userService.addUser(user);
        assertTrue(userService.authentication(user.getLogin(),user.getPassword()));
    }

    @Test
    public void userIsRealShouldReturnFalse(){
        UserRest user = users.get(0);
        userService.addUser(user);
        assertFalse(userService.authentication("login", "password"));
    }
    @Test(expected = LoginIsBusyException.class)
    public void checkAlreadyExistShouldThrowLoginIsBusyExceptinon(){
        UserRest user = users.get(0);
        userService.addUser(user);
        assertTrue(userService.checkAlreadyExist(user.getLogin(), user.getMail()));
    }
    @Test(expected = EmailIsBusyException.class)
    public void checkAlreadyExistShouldReturnEmailIsBusyExceptinon(){
        UserRest user = users.get(0);
        userService.addUser(user);
        assertTrue(userService.checkAlreadyExist("login", "mail"));
    }

}
