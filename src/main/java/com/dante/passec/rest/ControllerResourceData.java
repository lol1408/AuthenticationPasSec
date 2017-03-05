package com.dante.passec.rest;

import com.dante.passec.db.services.SessionService;
import com.dante.passec.excaption.ForbiddenExceprion;
import com.dante.passec.excaption.UnauthorizedException;
import com.dante.passec.excaption.UserNotFoundException;
import com.dante.passec.model.ResponseBody;
import com.dante.passec.model.ResourceData;
import com.dante.passec.db.services.ResourceDataService;
import com.dante.passec.db.services.UserRestService;
import com.dante.passec.model.Session;
import com.dante.passec.model.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static java.lang.System.*;
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
        ResponseBody<ResourceData> result = new ResponseBody<>();
        try {
            UserRest user;
            if((user=sessionService.sessionIsActual(token))!=null){
                resourceData.setUser(user);
            }
            else throw new UnauthorizedException();
            return resourceDataService.addResource(resourceData);
        }catch (Exception e){
            throw new ForbiddenExceprion();
        }
    }
    @RequestMapping(method = PUT)
    public ResourceData changeResourceData(@RequestBody ResourceData resourceData,
                                                         @RequestHeader(value = "token") Integer token)
    {
        ResponseBody<ResourceData> result = new ResponseBody<>();
        try {
            UserRest user;
            if((user=sessionService.sessionIsActual(token))!=null){
                resourceData.setUser(user);
            }
            else throw new UnauthorizedException();
            return resourceDataService.update(resourceData);
        }catch (Exception e){
            throw new ForbiddenExceprion();
        }
    }
    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseBody<ResourceData> deleteResource(@PathVariable("id") Long id,
                                                     @RequestHeader(value = "token") Integer token){
        ResponseBody<ResourceData> result = new ResponseBody<>();
        try {
            if(sessionService.sessionIsActual(token)!=null){
                resourceDataService.deleteResource(id);
            }
            else throw new UnauthorizedException();
            result.setResponse("200", "Удаление прошло успешно");
        }catch (Exception e){
            result.setResponse("202", "Ну удалось удалить запись");
            e.printStackTrace();
        }
        return result;
    }
}



