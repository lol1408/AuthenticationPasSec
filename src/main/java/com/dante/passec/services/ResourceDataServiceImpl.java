package com.dante.passec.services;

import com.dante.passec.crypt.CryptDao;
import com.dante.passec.crypt.CryptService;
import com.dante.passec.dao.ResourceDataDao;
import com.dante.passec.model.ResourceData;
import com.dante.passec.model.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
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

    /**
     * @param resourceData
     * function decrypt all passwords in List
     */
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
    /**
     * @return allResources which is in database
     */
    @Override
    public List<ResourceData> allResources() {
        List<ResourceData> resourcesByUser = resourceDataDao.findAll();
        decryptPasswords(resourcesByUser);
        return resourcesByUser;
    }
    /**
     * @param user
     * @return resources by user which pass in param
     */
    @Override
    public List<ResourceData> getResourcesByUser(UserRest user) {

        List<ResourceData> resourcesByUser = resourceDataDao.getResourcesByUser(user);
        decryptPasswords(resourcesByUser);
        return resourcesByUser;
    }

    /**
     * @param id
     * @return resource by id
     */
    @Override
    public ResourceData getResourceById(Long id) {
        ResourceData one = resourceDataDao.findOne(id);
        try {
            one.setPassword(cryptService.decrypt(one.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return one;
    }

    @Override
    public ResourceData addResource(ResourceData resourceData) {
        try {
            resourceData.setPassword(cryptService.encrypt(resourceData.getPassword()));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return resourceDataDao.save(resourceData);
    }

    @Override
    public ResourceData update(ResourceData resourceData) {
        try {
            resourceData.setPassword(cryptService.encrypt(resourceData.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resourceDataDao.saveAndFlush(resourceData);
    }

    @Override
    public void deleteResource(Long id) {
        resourceDataDao.delete(id);
    }
}
