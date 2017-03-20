package com.dante.passec.rest;

import com.dante.passec.db.services.SessionService;
import com.dante.passec.exception.ForbiddenExcepion;
import com.dante.passec.exception.UnauthorizedException;
import com.dante.passec.exception.UserAlreadyExistException;
import com.dante.passec.exception.UserNotFoundException;
import com.dante.passec.model.ResponseBody;
import com.dante.passec.model.UserRest;
import com.dante.passec.db.services.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(method = GET)
    public UserRest getUserCurrentUser(@RequestHeader("token") Integer token){
        UserRest user;
        if((user=sessionService.sessionIsActual(token))!=null) {
            return user;
        }
        else throw new UnauthorizedException();
    }

    @RequestMapping(method = POST)
    public UserRest registration(@RequestBody UserRest user){
        //Проверяем существует ли пользователь
        if(userService.checkAlreadyExist(user.getLogin())) throw new UserAlreadyExistException();
        try {
            return userService.addUser(user);
        }catch (Exception e){
            throw new ForbiddenExcepion();
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
            throw new ForbiddenExcepion();
        }
    }
    @RequestMapping(method = DELETE)
    public ResponseBody<UserRest> deleteUser(@RequestHeader(value = "token") Integer token)
    {
        ResponseBody<UserRest> result = new ResponseBody<>();
        UserRest user;
        if((user=sessionService.sessionIsActual(token))!=null)
        {
            userService.deleteUser(user.getId());
        }
        else throw new UserNotFoundException();

        try {
            result.setResponse("200", "Учетная запись удалена");
            return result;
        }catch (Exception e){
            throw new ForbiddenExcepion();
        }
    }
}
