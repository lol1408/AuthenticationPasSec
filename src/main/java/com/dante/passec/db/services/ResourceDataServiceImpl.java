package com.dante.passec.db.services;

import com.dante.passec.crypt.CryptService;
import com.dante.passec.db.dao.ResourceDataDao;
import com.dante.passec.exception.ForbiddenException;
import com.dante.passec.model.ResourceData;
import com.dante.passec.model.UserRest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implements for ResourceDataService interface
 * @author MakarenkoSergey
 * @version 1.0
 */
@Service
public class ResourceDataServiceImpl implements ResourceDataService {

    private final static Logger logger = Logger.getLogger(ResourceDataServiceImpl.class);
    @Autowired
    ResourceDataDao resourceDataDao;
    @Autowired
    CryptService cryptService;

    private void decryptPasswords(List<ResourceData> resourceData){
        ResourceData resource;
        for (int i=0; i<resourceData.size(); i++){
            try {
                resource = resourceData.get(i);
                resource.setPassword(cryptService.decrypt(resource.getPassword()));
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }

    public List<ResourceData> getResourcesByUser(UserRest user) {
        long start = System.nanoTime();
        List<ResourceData> resourcesByUser = resourceDataDao.getResourcesByUser(user);
        decryptPasswords(resourcesByUser);
        long end = System.nanoTime();
        logger.info("get resources by user id= " + user + ":" + (end-start));
        return resourcesByUser;
    }

    public ResourceData getResourceById(Long id) {
        try {
            ResourceData one = resourceDataDao.findOne(id);
            if(one==null) return null;
            one.setPassword(cryptService.decrypt(one.getPassword()));
            return one;
        } catch (Exception e) {
            logger.error("decrypt password is failed: ", e);
            throw new ForbiddenException();
        }
    }

    public ResourceData addResource(ResourceData resourceData) {
        //Copy received resource
        ResourceData tempResource = new ResourceData(resourceData);
        //Try encrypt password and write to baseу
        try {
            tempResource.setPassword(cryptService.encrypt(tempResource.getPassword()));
            resourceDataDao.save(tempResource);
            resourceData.setId(tempResource.getId());
            return resourceData;
        }catch (Exception ex){
            logger.equals("add resource is failed");
            throw new ForbiddenException();
        }
    }

    public ResourceData update(ResourceData resourceData) {
        //Copy received resource
        ResourceData tempResource = new ResourceData(resourceData);
        //Try encrypt password and write to base
        try {
            tempResource.setPassword(cryptService.encrypt(tempResource.getPassword()));
            resourceDataDao.saveAndFlush(tempResource);
            resourceData.setId(tempResource.getId());
            return resourceData;
        } catch (Exception e) {
            logger.error("change resource is failed: ", e);
            throw new ForbiddenException();
        }
    }

    public void deleteResource(Long id) {
        try {
            resourceDataDao.delete(id);
        }catch (Exception ex){
            logger.error("delete resource is failed");
            throw new ForbiddenException();
        }
    }
}
