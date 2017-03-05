package com.dante.passec.db.services;

import com.dante.passec.crypt.CryptService;
import com.dante.passec.db.dao.ResourceDataDao;
import com.dante.passec.model.ResourceData;
import com.dante.passec.model.UserRest;
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
                e.printStackTrace();
            }
        }
    }

    public List<ResourceData> allResources() {
        List<ResourceData> resourcesByUser = resourceDataDao.findAll();
        decryptPasswords(resourcesByUser);
        return resourcesByUser;
    }

    public List<ResourceData> getResourcesByUserId(Long id) {
        long start = System.nanoTime();
        List<ResourceData> resourcesByUser = resourceDataDao.getResourcesByUserId(id);
        decryptPasswords(resourcesByUser);
        long end = System.nanoTime();
        System.out.println("get resources by user: " + (end-start));
        return resourcesByUser;
    }

    public ResourceData getResourceById(Long id) {
        ResourceData one = resourceDataDao.findOne(id);
        try {
            one.setPassword(cryptService.decrypt(one.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return one;
    }

    public ResourceData addResource(ResourceData resourceData) {
        long start = System.nanoTime();
        try {
            resourceData.setPassword(cryptService.encrypt(resourceData.getPassword()));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        ResourceData save = resourceDataDao.save(resourceData);
        long end = System.nanoTime();
        System.out.println("add resource: " + (end-start));
        return save;
    }

    public ResourceData update(ResourceData resourceData) {
        try {
            resourceData.setPassword(cryptService.encrypt(resourceData.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resourceDataDao.saveAndFlush(resourceData);
    }

    public void deleteResource(Long id) {
        resourceDataDao.delete(id);
    }
}
