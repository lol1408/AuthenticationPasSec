package com.dante.passec.rest;

import com.dante.passec.model.AjaxResponseBody;
import com.dante.passec.model.UserRest;
import com.dante.passec.services.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

//    @RequestMapping(value = "/{login}", method = RequestMethod.GET)
//    public UserRest getUserByLogin(@PathVariable("login") String login){
//        return userRestService.userByLogin(login);
//    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<UserRest> getAllUsers(){
        return userRestService.allUsers();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public AjaxResponseBody<UserRest> saveUser(@RequestBody UserRest user){
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
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public AjaxResponseBody<UserRest> updateUser(@RequestBody UserRest user)
    {
        AjaxResponseBody<UserRest> result = new AjaxResponseBody<>();
        try {
            userRestService.updateUser(user);
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
