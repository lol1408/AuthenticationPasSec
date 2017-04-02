package com.dante.passec.utils;

import com.dante.passec.model.UserRest;

/**
 * Class for managing UserRest classes for create object's Class UserRest
 * @author Dante de Braso
 */
public class UserRestManager {
    /**
     * It's factory method for UserRest class
     * @param login
     * @param password
     * @return UserRest object
     */
    public static UserRest createUser(String login, String password, String mail){
        UserRest userRest = new UserRest(login, password, mail, true);
        return userRest;
    }
}
