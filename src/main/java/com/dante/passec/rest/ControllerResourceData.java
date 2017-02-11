package com.dante.passec.rest;

import com.dante.passec.model.AjaxResponseBody;
import com.dante.passec.model.ResourceData;
import com.dante.passec.model.UserRest;
import com.dante.passec.services.ResourceDataService;
import com.dante.passec.services.UserRestService;
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
@RequestMapping(value = "/resource")
public class ControllerResourceData {

    @Autowired
    ResourceDataService resourceDataService;
    @Autowired
    UserRestService userRestService;

    @RequestMapping(value = "/byuser/{userId}", method = GET)
    public List<ResourceData> getResourcesByUser(@PathVariable("userId") Long id){
        UserRest user = userRestService.userById(id);
        return resourceDataService.getResourcesByUser(user);
    }
    @RequestMapping(value = "/{id}", method = GET)
    public ResourceData getResourceById(@PathVariable("id") Long id){
        return resourceDataService.getResourceById(id);
    }
    @RequestMapping(value = "/{id}", method = POST)
    public AjaxResponseBody<ResourceData> saveResourceData(@RequestBody ResourceData resourceData, @PathVariable("id") Long id)
    {
        AjaxResponseBody<ResourceData> result = new AjaxResponseBody<>();
        try {
            UserRest userRest = userRestService.userById(id);
            resourceData.setUser(userRest);
            resourceDataService.addResource(resourceData);
            result.setCode("200");
            result.setMsg("Запись успешно добавлена");
        }catch (Exception e){
            result.setCode("202");
            result.setMsg("Ну удалось добавить запись");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping(value = "/{id}", method = PUT)
    public AjaxResponseBody<ResourceData> changeResourceData(@RequestBody ResourceData resourceData, @PathVariable("id") Long id)
    {
        AjaxResponseBody<ResourceData> result = new AjaxResponseBody<>();
        try {
            UserRest userRest = userRestService.userById(id);
            resourceData.setUser(userRest);
            resourceDataService.update(resourceData);
            result.setOneResult(resourceData);
            result.setCode("200");
            result.setMsg("Изменения приняты");
        }catch (Exception e){
            result.setCode("202");
            result.setMsg("Ну удалось изменить запись");
            e.printStackTrace();
        }
        return result;
    }
    @RequestMapping(value = "/{id}", method = DELETE)
    public AjaxResponseBody<ResourceData> deleteResource(@PathVariable("id") Long id){
        AjaxResponseBody<ResourceData> result = new AjaxResponseBody<>();
        try {
            UserRest userRest = userRestService.userById(id);
            resourceDataService.deleteResource(id);
            result.setCode("200");
            result.setMsg("Удаление прошло успешно");
        }catch (Exception e){
            result.setCode("202");
            result.setMsg("Ну удалось удалить запись");
            e.printStackTrace();
        }
        return result;
    }
}
