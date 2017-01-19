package com.dante.passec.services;

import com.dante.passec.dao.ResourceDataDao;
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

    /**
     * @return allResources which is in database
     */
    @Override
    public List<ResourceData> allResources() {
        return resourceDataDao.findAll();
    }

    /**
     * @param user
     * @return resources by user which pass in param
     */
    @Override
    public List<ResourceData> getResourcesByUser(UserRest user) {
        return resourceDataDao.getResourcesByUser(user);
    }

    /**
     * @param id
     * @return resource by id
     */
    @Override
    public ResourceData getResourceById(Long id) {
        return resourceDataDao.findOne(id);
    }

    @Override
    public ResourceData addResource(ResourceData resourceData) {
        return resourceDataDao.save(resourceData);
    }

    @Override
    public ResourceData update(ResourceData resourceData) {
        return resourceDataDao.saveAndFlush(resourceData);
    }

    @Override
    public void deleteResource(Long id) {
        resourceDataDao.delete(id);
    }
}
