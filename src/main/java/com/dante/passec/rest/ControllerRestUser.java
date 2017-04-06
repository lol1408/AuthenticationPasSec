package com.dante.passec.rest;

import com.dante.passec.db.services.SessionService;
import com.dante.passec.exception.*;
import com.dante.passec.mail.MailService;
import com.dante.passec.mail.RandomTokenService;
import com.dante.passec.model.UserRest;
import com.dante.passec.db.services.UserRestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * RestController for RestUser
 * @author Makarenko Sergey
 * @version 1.0
 * Created by We on 22.12.2016.
 */
@RestController
@RequestMapping(value = "/users/")
public class ControllerRestUser {
    private static final Logger logger = Logger.getLogger(ControllerRestUser.class);
    @Autowired
    UserRestService userService;
    @Autowired
    SessionService sessionService;
    @Autowired
    RandomTokenService randomTokenService;
    @Autowired
    MailService mailService;
    @RequestMapping(method = GET)
    public UserRest getUserCurrentUser(@RequestHeader("token") Integer token){
        UserRest user;
        if((user=sessionService.sessionIsActual(token))!=null) {
            return user;
        }
        else throw new UnauthorizedException();
    }
    @RequestMapping(method = POST)
    public void registration(@RequestBody UserRest user) throws LoginIsBusyException{
        userService.checkAlreadyExist(user.getLogin(), user.getMail());
        user.setActive(false);
        UserRest userRest = userService.addUser(user);
        Integer randomToken = randomTokenService.getRandomToken(userRest.getMail());
        mailService.sendMail(randomToken, userRest.getLogin(), userRest.getMail());
    }

    @RequestMapping(method = PUT, value = "/password")
    public UserRest updatePassword(@RequestBody String password,
                                   @RequestHeader(value = "token") Integer token,
                                   @RequestHeader(value = "oldpassword") String oldPassword)
    {
        UserRest userInBase = sessionService.sessionIsActual(token);
        if(!userInBase.getPassword().equals(oldPassword)) throw new UserNotFoundException();
            userInBase.setPassword(password);
            return userService.updateUser(userInBase);
    }

    @ExceptionHandler(LoginIsBusyException.class)
    public void handleException(HttpServletResponse response, LoginIsBusyException ex) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Login is busy");
    }

}
