package com.dante.passec.rest;

import com.dante.passec.db.services.UserRestService;
import com.dante.passec.exception.ForbiddenException;
import com.dante.passec.exception.NotFoundException;
import com.dante.passec.mail.MailService;
import com.dante.passec.mail.RandomTokenService;
import com.dante.passec.model.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/confirm/")
public class ControllerConfirm {

    @Autowired
    UserRestService userService;
    @Autowired
    RandomTokenService tokenService;
    @Autowired
    MailService mailService;

    @RequestMapping(method = GET, value = "/{token}")
    public String confirmAccount(@PathVariable("token") Integer token){
        String mailByToken = tokenService.getMailByToken(token);
        UserRest userRest = userService.userByMail(mailByToken);
        Boolean aBoolean = tokenService.confirmRandomToken(token);
        if(aBoolean){
            userRest.setActive(true);
            userService.updateUser(userRest);
            return "info-confirm";
        }else throw new NotFoundException();
    }

    @RequestMapping(method = GET, value = "/annulment/{token}")
    public String annulmentConfirm(@PathVariable(value = "token") Integer token){
        String mailByToken = tokenService.getMailByToken(token);
        UserRest userRest = userService.userByMail(mailByToken);

        Boolean aBoolean = tokenService.confirmRandomToken(token);
        if(aBoolean){
            userService.deleteUser(userRest.getId());
            return "info-annulment";
        }else throw new ForbiddenException();
    }

    @RequestMapping(method = GET, value = "/reget")
    public String reGetRandomToken(@RequestHeader(value = "mail") String mail){
        UserRest userRest = userService.userByMail(mail);
        if(userRest == null)
            throw new NotFoundException();
        Integer randomToken = tokenService.reGetRandomToken(mail);
        mailService.sendMail(randomToken, userRest.getLogin(), mail);
        return "info-get-token";
    }
}
