package com.dante.passec.rest;

import com.dante.passec.db.services.SessionService;
import com.dante.passec.model.ResourceData;
import com.dante.passec.db.services.ResourceDataService;
import com.dante.passec.db.services.UserRestService;
import com.dante.passec.model.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


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

    @GetMapping
    public List<ResourceData> getResourcesByUser(@RequestHeader("token") Integer token) throws Exception {
        UserRest user = sessionService.sessionIsActual(token);
        return resourceDataService.getResourcesByUser(user);
    }

    @PostMapping
    public ResourceData saveResourceData(@RequestBody ResourceData resourceData,
                                                       @RequestHeader(value = "token") Integer token)
    {
        UserRest user=sessionService.sessionIsActual(token);
        resourceData.setUser(user);
        return resourceDataService.addResource(resourceData);
    }

    @PutMapping
    public ResourceData changeResourceData(@RequestBody ResourceData resourceData,
                                                         @RequestHeader(value = "token") Integer token)
    {
        UserRest userRest = sessionService.sessionIsActual(token);
        resourceData.setUser(userRest);
        return resourceDataService.update(resourceData);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteResource(@PathVariable("id") Long id,
                                                           @RequestHeader(value = "token") Integer token){
        sessionService.sessionIsActual(token);
        resourceDataService.deleteResource(id);
    }
}



