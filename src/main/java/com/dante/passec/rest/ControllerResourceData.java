package com.dante.passec.rest;

import com.dante.passec.db.services.SessionService;
import com.dante.passec.exception.ForbiddenException;
import com.dante.passec.exception.UnauthorizedException;
import com.dante.passec.model.CustomResponseBody;
import com.dante.passec.model.ResourceData;
import com.dante.passec.db.services.ResourceDataService;
import com.dante.passec.db.services.UserRestService;
import com.dante.passec.model.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * RestController for ResourceData
 * @author Makarenko Sergey
 * @version 1.0
 * Created by We on 01.30.2016.
 */

@RestController
@RequestMapping(value = "/resources/")
public class ControllerResourceData {

    @Autowired
    ResourceDataService resourceDataService;
    @Autowired
    UserRestService userRestService;
    @Autowired
    SessionService sessionService;

    @RequestMapping(method = GET)
    public List<ResourceData> getResourcesByUser(@RequestHeader("token") Integer token) throws Exception {
        UserRest user;
        if((user=sessionService.sessionIsActual(token))!=null){
            return resourceDataService.getResourcesByUserId(user.getId());
        }
        else throw new UnauthorizedException();
    }

    @RequestMapping(method = POST)
    public ResourceData saveResourceData(@RequestBody ResourceData resourceData,
                                                       @RequestHeader(value = "token") Integer token)
    {
        UserRest user;
        if((user=sessionService.sessionIsActual(token))!=null){
            resourceData.setUser(user);
        }
        else throw new UnauthorizedException();
        try {
            return resourceDataService.addResource(resourceData);
        }catch (Exception e){
            throw new ForbiddenException();
        }
    }
    @RequestMapping(method = PUT)
    public ResourceData changeResourceData(@RequestBody ResourceData resourceData,
                                                         @RequestHeader(value = "token") Integer token)
    {
        UserRest user;
        if((user=sessionService.sessionIsActual(token))!=null){
            resourceData.setUser(user);
        }
        else throw new UnauthorizedException();
        try {
            return resourceDataService.update(resourceData);
        }catch (Exception e){
            throw new ForbiddenException();
        }
    }
    @RequestMapping(value = "/{id}", method = DELETE)
    public CustomResponseBody<ResourceData> deleteResource(@PathVariable("id") Long id,
                                                           @RequestHeader(value = "token") Integer token){
        CustomResponseBody<ResourceData> result = new CustomResponseBody<>();
        if(sessionService.sessionIsActual(token)!=null){
            resourceDataService.deleteResource(id);
        }
        else throw new UnauthorizedException();
        try {
            result.setResponse("200", "Удаление прошло успешно");
        }catch (Exception e){
            throw new ForbiddenException();
        }
        return result;
    }
}



