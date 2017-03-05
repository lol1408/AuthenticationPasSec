package com.dante.passec.rest;

import com.dante.passec.db.services.SessionService;
import com.dante.passec.excaption.UnauthorizedException;
import com.dante.passec.excaption.UserNotFoundException;
import com.dante.passec.model.ResponseBody;
import com.dante.passec.model.Session;
import com.dante.passec.db.services.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * RestController for authentication
 * @author Makarenko Sergey
 * @version 1.0
 * Created by We on 01.30.2016.
 */
@RestController
@RequestMapping("/")
public class AuthenticationController {

    @Autowired
    UserRestService userService;
    @Autowired
    SessionService sessionService;

    @RequestMapping(path = "login", method = GET)
    public Integer login(@RequestHeader(value = "login") String login,
                         @RequestHeader(value = "password") String password){
        if(userService.userIsReal(login, password))
            return sessionService.addSession(userService.userByLogin(login)).getToken();
        else throw new UserNotFoundException();
    }
    @RequestMapping(path = "logout", method = GET)
    public ResponseBody<Session> logout(@RequestHeader(value = "token") Integer token){
        ResponseBody<Session> responseBody = new ResponseBody<>();
        if(sessionService.sessionIsActual(token)!=null)
        {
            sessionService.setNotIncluding(sessionService.findByToken(token));
            responseBody.setResponse("Вы успешно вышли", "200");
            return responseBody;
        }else throw new UnauthorizedException();
    }
}
