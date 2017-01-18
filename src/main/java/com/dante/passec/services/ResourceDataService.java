package com.dante.passec.services;

import com.dante.passec.model.ResourceData;
import com.dante.passec.model.UserRest;

import java.util.List;

/**
 * Service for model ResourceData.
 */
public interface ResourceDataService {

    List<ResourceData> allResources();
    List<ResourceData> getResourcesByUser(UserRest user);
    ResourceData getResourceById(Long id);
    ResourceData addResource(ResourceData resourceData);
    ResourceData update(ResourceData resourceData);
    void deleteResource(Long id);

}
