package com.dante.passec.rest;

import com.dante.passec.db.services.UserRestService;
import com.dante.passec.mail.RandomTokenService;
import com.dante.passec.model.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("dev")
@RestController
@RequestMapping(value = "/dev/")
public class ControllerDev {
    @Autowired
    RandomTokenService tokenService;
    @Autowired
    UserRestService userService;

    @GetMapping(value = "/{login}")
    public String devMethod(@PathVariable("login") String login){
        UserRest user = userService.userByLoginOrMail(login, "");
        user.setActive(true);
        userService.updateUser(user);

        Integer randomToken = tokenService.getRandomToken(user.getMail());
        tokenService.confirmRandomToken(randomToken);
        return "Thank you";
    }

}
