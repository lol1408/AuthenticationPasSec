package com.dante.passec.rest;

import com.dante.passec.model.UserRest;
import com.dante.passec.services.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
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
public class ContollerRestUser {

    @Autowired
    UserRestService userRestService;

    @RequestMapping(value = "{idRestUser}", method = RequestMethod.GET)
    public UserRest userRest(Long id){
        return userRestService.userById(id);
    }

    @RequestMapping(value = "/{login}", method = RequestMethod.GET)
    public UserRest userRest(@PathVariable("login") String login){
        return userRestService.userByLogin(login);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<UserRest> userRest(){
        return userRestService.allUsers();
    }

    @RequestMapping(value = "/add-user", method = RequestMethod.POST)
    public 

}
