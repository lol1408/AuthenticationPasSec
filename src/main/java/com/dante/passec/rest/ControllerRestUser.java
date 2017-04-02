package com.dante.passec.rest;

import com.dante.passec.db.services.SessionService;
import com.dante.passec.exception.ForbiddenException;
import com.dante.passec.exception.UnauthorizedException;
import com.dante.passec.exception.UserAlreadyExistException;
import com.dante.passec.mail.MailService;
import com.dante.passec.mail.RandomTokenService;
import com.dante.passec.model.CustomResponseBody;
import com.dante.passec.model.UserRest;
import com.dante.passec.db.services.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public void registration(@RequestBody UserRest user){
        //Проверяем существует ли пользователь
        int i = userService.checkAlreadyExist(user.getLogin(), user.getMail());
        if(i==1) throw new UserAlreadyExistException();
        else if(i==2) throw new ForbiddenException();
        try {
            user.setActive(false);
            UserRest userRest = userService.addUser(user);
            Integer randomToken = randomTokenService.getRandomToken(userRest.getMail());
            mailService.sendMail(randomToken, userRest.getLogin(), userRest.getMail());
        }catch (Exception e){
            throw new ForbiddenException();
        }
    }
    @RequestMapping(method = PUT)
    public UserRest updateUser(@RequestBody UserRest user,
                                             @RequestHeader(value = "token") Integer token)
    {
        UserRest userInBase;
        if((userInBase=sessionService.sessionIsActual(token))!=null){
            userInBase.setLogin(user.getLogin());
            userInBase.setPassword(user.getPassword());
        }
        else throw new UnauthorizedException();
        try {
            return userService.updateUser(userInBase);
        }catch (Exception e){
            throw new ForbiddenException();
        }
    }
    @RequestMapping(method = DELETE)
    public CustomResponseBody<UserRest> deleteUser(@RequestHeader(value = "token") Integer token)
    {
        CustomResponseBody<UserRest> result = new CustomResponseBody<>();
        UserRest user;
        if((user=sessionService.sessionIsActual(token))!=null)
        {
            userService.deleteUser(user.getId());
        }
        else throw new UnauthorizedException();

        try {
            result.setResponse("200", "Учетная запись удалена");
            return result;
        }catch (Exception e){
            throw new ForbiddenException();
        }
    }
}
