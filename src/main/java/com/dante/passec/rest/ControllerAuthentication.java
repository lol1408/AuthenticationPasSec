package com.dante.passec.rest;

import com.dante.passec.db.services.SessionService;
import com.dante.passec.exception.LockedException;
import com.dante.passec.exception.UnauthorizedException;
import com.dante.passec.exception.NotFoundException;
import com.dante.passec.mail.MailService;
import com.dante.passec.model.CustomResponseBody;
import com.dante.passec.model.Session;
import com.dante.passec.db.services.UserRestService;
import com.dante.passec.model.UserRest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

import static com.dante.passec.Utils.toSha1;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * RestController for authentication
 * @author Makarenko Sergey
 * @version 1.0
 * Created by We on 01.30.2016.
 */
@RestController
@RequestMapping("/")
public class ControllerAuthentication {
    Logger logger = Logger.getLogger(ControllerAuthentication.class);
    @Autowired
    UserRestService userService;
    @Autowired
    SessionService sessionService;
    @Autowired
    MailService mailService;

    @RequestMapping(path = "login", method = GET)
    public Integer login(@RequestHeader(value = "login") String login,
                         @RequestHeader(value = "password") String password){
        if(userService.userIsReal(login, password)) {
            Integer token = sessionService.addSession(userService.userByLogin(login)).getToken();
            if(token == null)
                throw new LockedException();
            return token;
        }
        else throw new NotFoundException();
    }
    @RequestMapping(path = "logout", method = GET)
    public CustomResponseBody<Session> logout(@RequestHeader(value = "token") Integer token){
        CustomResponseBody<Session> responseBody = new CustomResponseBody<>();
        if(sessionService.sessionIsActual(token)!=null)
        {
            sessionService.setNotIncluding(sessionService.findByToken(token));
            responseBody.setResponse("Вы успешно вышли", "200");
            return responseBody;
        }else throw new UnauthorizedException();
    }
    @RequestMapping(path = "getnewpass", method = GET)
    public void forget(@RequestHeader(value = "email") String email){
        UserRest userRest;
        try {
            userRest = userService.userByMail(email);
            if(userRest==null) throw new NotFoundException();
            Random random = new Random();
            Integer randomPassword = (random.nextInt(899999))+100000;
            userRest.setPassword(toSha1(randomPassword));
            userService.updateUser(userRest);
            String password = randomPassword.toString();
            mailService.sendPassword(password, userRest.getLogin(), email);
        }catch (Exception e){
            logger.error("User not found: " + e.getStackTrace());
            e.getStackTrace();
        }
    }
}
