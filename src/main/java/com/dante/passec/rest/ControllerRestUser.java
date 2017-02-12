package com.dante.passec.rest;

import com.dante.passec.model.AjaxResponseBody;
import com.dante.passec.model.UserRest;
import com.dante.passec.services.UserRestService;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * RestController for RestUser
 * @author Makarenko Sergey
 * @version 1.0
 * Created by We on 22.12.2016.
 */
@RestController
@RequestMapping(value = "/users")
public class ControllerRestUser {

    @Autowired
    UserRestService userRestService;

    @RequestMapping(value = "/{idUser}", method = GET)
    public UserRest getUserById(@PathVariable("idUser") Long id){
        return userRestService.userById(id);
    }

    @RequestMapping(value = "/", method = GET)
    public List<UserRest> getAllUsers(){
        return userRestService.allUsers();
    }

    @RequestMapping(value = "/", method = POST)
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
    @RequestMapping(value = "/", method = PUT)
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
    @RequestMapping(value = "/{idUser}", method = DELETE)
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
