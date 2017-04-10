package com.dante.passec.rest;

import com.dante.passec.db.services.SessionService;
import com.dante.passec.exception.UserNotFoundException;
import com.dante.passec.mail.MailService;
import com.dante.passec.db.services.UserRestService;
import com.dante.passec.model.UserRest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

import static com.dante.passec.Utils.toSha1;

/**
 * RestController for authentication
 * @author Makarenko Sergey
 * @version 1.0
 * Created by We on 01.30.2016.
 */
@RestController
@RequestMapping("/")
public class ControllerAuthentication {
    private static final Logger logger = Logger.getLogger(ControllerAuthentication.class);
    @Autowired
    UserRestService userService;
    @Autowired
    SessionService sessionService;
    @Autowired
    MailService mailService;

    @GetMapping(path = "login")
    public Integer login(@RequestHeader(value = "login") String login,
                         @RequestHeader(value = "password") String password){
        if(userService.authentication(login, password)) {
            Integer token = sessionService.addSession(userService.userByLogin(login)).getToken();
            return token;
        }
        else throw new UserNotFoundException();
    }

    @GetMapping(path = "logout")
    public void logout(@RequestHeader(value = "token") Integer token){
        sessionService.sessionIsActual(token);
        sessionService.setNotIncluding(sessionService.findByToken(token));
    }

    @GetMapping(path = "getnewpass")
    public void forget(@RequestHeader(value = "email") String email){
        UserRest userRest;
        userRest = userService.userByMail(email);
        if(userRest==null) throw new UserNotFoundException();
        Random random = new Random();
        Integer randomPassword = (random.nextInt(899999))+100000;
        userRest.setPassword(toSha1(randomPassword));
        userService.updateUser(userRest);
        String password = randomPassword.toString();
        mailService.sendPassword(password, userRest.getLogin(), email);

    }
}
