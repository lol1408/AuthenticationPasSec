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
    public static UserRest createUser(String login, String password){
        UserRest userRest = new UserRest();
        userRest.setLogin(login);
        userRest.setPassword(password);
        return userRest;
    }
}
