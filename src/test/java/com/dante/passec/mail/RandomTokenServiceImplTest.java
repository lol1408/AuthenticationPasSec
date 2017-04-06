package com.dante.passec.mail;

import com.dante.passec.model.UserRest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RandomTokenServiceImplTest {

    @Autowired
    RandomTokenService randomTokenService;

    @Test
    public void getRandomTokenShouldReturnNumberBetween1000and9999(){
        UserRest user = newUser("new@mail.ru");
        for(int i = 0; i < 10000; i++) {
            Integer randomToken = randomTokenService.getRandomToken(user.getMail());
            assertTrue(randomToken >= 1000 && randomToken <= 9999);
        }
    }
    @Test
    public void confirmTokenShouldBeSuccess(){
        UserRest user = newUser("sercond@mail.ru");
        Integer randomToken = randomTokenService.getRandomToken(user.getMail());
        assertTrue(randomTokenService.confirmRandomToken(randomToken));
        assertFalse(randomTokenService.confirmRandomToken(randomToken));
    }

    private UserRest newUser(String mail){
        UserRest user = new UserRest("login1", "password1", mail, false);
        return user;
    }


}