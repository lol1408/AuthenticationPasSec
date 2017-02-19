package com.dante.passec.rest;

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

    @RequestMapping(method = GET)
    public UserRest getUserCurrentUser(@RequestHeader("login") String login,
                                       @RequestHeader("password") String password){
        if(userService.userIsReal(login, password))
            return userService.userByLogin(login);
        else throw new UserNotFoundException();
    }

    @RequestMapping(method = POST)
    public ResponseBody<UserRest> registration(@RequestBody UserRest user){
        ResponseBody<UserRest> result = new ResponseBody<>();
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
                                             @RequestHeader(value = "login") String login,
                                             @RequestHeader(value = "password") String password )
    {
        ResponseBody<UserRest> result = new ResponseBody<>();
        UserRest userInBase;
        try {
            if(userService.userIsReal(login, password))
                userInBase = userService.userByLogin(login);
            else throw new UserNotFoundException();
            userInBase.setLogin(user.getLogin());
            userInBase.setPassword(user.getPassword());
            userService.updateUser(userInBase);
            result.setOneResult(userInBase);
            result.setResponse("200", "Пользователь успешно изменен");
        }catch (Exception e){
            result.setResponse("202", "Не удалось изменить учутную запись пользователя");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping(method = DELETE)
    public ResponseBody<UserRest> deleteUser(@RequestHeader(value = "login") String login,
                                             @RequestHeader(value = "password") String password)
    {
        ResponseBody<UserRest> result = new ResponseBody<>();
        try {
            if(userService.userIsReal(login, password))
                userService.deleteUser(userService.userByLogin(login).getId());
            else throw new UserNotFoundException();
            result.setResponse("200", "Учетная запись удалена");
        }catch (Exception e){
            result.setResponse("202", "Не удалось учетную запись пользователя");
            e.printStackTrace();
        }
        return result;
    }
}
