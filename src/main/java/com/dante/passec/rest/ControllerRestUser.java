package com.dante.passec.rest;

import com.dante.passec.db.services.SessionService;
import com.dante.passec.excaption.UnauthorizedException;
import com.dante.passec.excaption.UserAlreadyExistException;
import com.dante.passec.excaption.UserNotFoundException;
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
    public ResponseBody<UserRest> registration(@RequestBody UserRest user){
        ResponseBody<UserRest> result = new ResponseBody<>();
        //Проверяем существует ли пользователь
        if(userService.checkAlreadyExist(user.getLogin())) throw new UserAlreadyExistException();
        try {
            userService.addUser(user);
            result.setResponse("200", "Пользователь успешно добавлен");
            result.setOneResult(user);
        }catch (Exception e){
            result.setResponse("202", "Не удалось добавить пользователя");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping(method = PUT)
    public ResponseBody<UserRest> updateUser(@RequestBody UserRest user,
                                             @RequestHeader(value = "token") Integer token)
    {
        ResponseBody<UserRest> result = new ResponseBody<>();
        UserRest userInBase;
        try {
            if((userInBase=sessionService.sessionIsActual(token))!=null){
                userInBase.setLogin(user.getLogin());
                userInBase.setPassword(user.getPassword());
                userService.updateUser(userInBase);
                result.setOneResult(userInBase);
                result.setResponse("200", "Пользователь успешно изменен");
            }
            else throw new UnauthorizedException();
        }catch (Exception e){
            result.setResponse("202", "Не удалось изменить учутную запись пользователя");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping(method = DELETE)
    public ResponseBody<UserRest> deleteUser(@RequestHeader(value = "token") Integer token)
    {
        ResponseBody<UserRest> result = new ResponseBody<>();
        try {
            UserRest user;
            if((user=sessionService.sessionIsActual(token))!=null)
            {
                userService.deleteUser(user.getId());
            }
            else throw new UserNotFoundException();
            result.setResponse("200", "Учетная запись удалена");
        }catch (Exception e){
            result.setResponse("202", "Не удалось учетную запись пользователя");
            e.printStackTrace();
        }
        return result;
    }
}
