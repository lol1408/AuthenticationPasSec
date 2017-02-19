package com.dante.passec.rest;

import com.dante.passec.excaption.UserNotFoundException;
import com.dante.passec.model.UserRest;
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
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    UserRestService userService;

    @RequestMapping(method = GET)
    public UserRest login(@RequestHeader(value = "login") String login,
                          @RequestHeader(value = "password") String password){
        if(userService.userIsReal(login, password))
            return userService.userByLogin(login);
        else throw new UserNotFoundException();
    }

}
