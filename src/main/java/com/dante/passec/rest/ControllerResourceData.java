package com.dante.passec.rest;

import com.dante.passec.excaption.UserNotFoundException;
import com.dante.passec.model.ResponseBody;
import com.dante.passec.model.ResourceData;
import com.dante.passec.db.services.ResourceDataService;
import com.dante.passec.db.services.UserRestService;
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

    @RequestMapping(method = GET)
    public List<ResourceData> getResourcesByUser(@RequestHeader(value = "login") String login,
                                                 @RequestHeader(value = "password") String password) throws Exception {
        if(userRestService.userIsReal(login, password))
        return resourceDataService.getResourcesByUser(userRestService.userByLogin(login));
        else throw new UserNotFoundException();
    }

    @RequestMapping(method = POST)
    public ResponseBody<ResourceData> saveResourceData(@RequestBody ResourceData resourceData,
                                                       @RequestHeader(value = "login") String login,
                                                       @RequestHeader(value = "password") String password)
    {
        ResponseBody<ResourceData> result = new ResponseBody<>();
        try {
            if(userRestService.userIsReal(login, password))
            resourceData.setUser(userRestService.userByLogin(login));
            else throw new UserNotFoundException();
            resourceDataService.addResource(resourceData);
            result.setOneResult(resourceData);
            result.setResponse("200", "Ну удалось добавить запись");
        }catch (Exception e){
            result.setResponse("202", "Не удалось добавить запись");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping(method = PUT)
    public ResponseBody<ResourceData> changeResourceData(@RequestBody ResourceData resourceData,
                                                         @RequestHeader(value = "login") String login,
                                                         @RequestHeader(value = "password") String password)
    {
        ResponseBody<ResourceData> result = new ResponseBody<>();
        try {
            if(userRestService.userIsReal(login, password))
                resourceData.setUser(userRestService.userByLogin(login));
            else throw new UserNotFoundException();
            resourceDataService.update(resourceData);
            result.setOneResult(resourceData);
            result.setResponse("200", "Изменения приняты");
        }catch (Exception e){
            result.setResponse("202", "Ну удалось изменить запись");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseBody<ResourceData> deleteResource(@PathVariable("id") Long id,
                                                     @RequestHeader(value = "login") String login,
                                                     @RequestHeader(value = "password") String password){
        ResponseBody<ResourceData> result = new ResponseBody<>();
        try {
            if(userRestService.userIsReal(login, password))
                resourceDataService.deleteResource(id);
            else throw new UserNotFoundException();
            result.setResponse("200", "Удаление прошло успешно");
        }catch (Exception e){
            result.setResponse("202", "Ну удалось удалить запись");
            e.printStackTrace();
        }
        return result;
    }
}



