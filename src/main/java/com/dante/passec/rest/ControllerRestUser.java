package com.dante.passec.rest;

import com.dante.passec.model.AjaxResponseBody;
import com.dante.passec.model.UserRest;
import com.dante.passec.services.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RestController for RestUser
 * @author Makarenko Sergey
 * @version 1.0
 * Created by We on 22.12.2016.
 */
@RestController
@RequestMapping(value = "/restusers")
public class ControllerRestUser {

    @Autowired
    UserRestService userRestService;

    @RequestMapping(value = "/{idUser}", method = RequestMethod.GET)
    public UserRest getUserById(@PathVariable("idUser") Long id){
        return userRestService.userById(id);
    }

    @RequestMapping(value = "/{login}", method = RequestMethod.GET)
    public UserRest getUserByLogin(@PathVariable("login") String login){
        return userRestService.userByLogin(login);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<UserRest> getAllUsers(){
        return userRestService.allUsers();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AjaxResponseBody<UserRest> saveUser(UserRest user){
        AjaxResponseBody<UserRest> result = new AjaxResponseBody<>();
        try {
            userRestService.addUser(user);
            result.setCode("200");
            result.setOneResult(user);
            result.setMsg("Пользователь успешно добавлен");
        }catch (Exception e){
            result.setCode("202");
            result.setMsg("Не удалось добавить пользователя");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping(value = "/change", method = RequestMethod.PUT)
    public AjaxResponseBody<UserRest> updateUser(UserRest user)
    {
        AjaxResponseBody<UserRest> result = new AjaxResponseBody<>();
        try {
            UserRest userRest = userRestService.userByLogin(user.getLogin());
            userRestService.updateUser(userRest);
            result.setOneResult(user);
            result.setCode("200");
            result.setMsg("Пользователь успешно изменен");
        }catch (Exception e){
            result.setCode("202");
            result.setMsg("Не удалось изменить учутную запись пользователя");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping(value = "/{idUser}", method = RequestMethod.DELETE)
    public AjaxResponseBody<UserRest> deleteUser(@PathVariable("idUser") Long id)
    {
        AjaxResponseBody<UserRest> result = new AjaxResponseBody<>();
        try {
            userRestService.deleteUser(id);
            result.setCode("200");
            result.setMsg("Пользователь успешно удален");
        }catch (Exception e){
            result.setCode("202");
            result.setMsg("Не удалось учетную запись пользователя");
            e.printStackTrace();
        }
        return result;
    }
}
