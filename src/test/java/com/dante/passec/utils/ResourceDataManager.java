package com.dante.passec.utils;

import com.dante.passec.model.ResourceData;
import com.dante.passec.model.UserRest;

/**
 * Class for managing UserRest classes for create object's Class UserRest
 * @author Dante de Braso
 */
public class ResourceDataManager {
    public static ResourceData resourceData(String login, String password, UserRest user){
        return new ResourceData(login, password, user);
    }
}
