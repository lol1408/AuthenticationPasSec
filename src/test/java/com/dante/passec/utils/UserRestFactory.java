package com.dante.passec.utils;

import com.dante.passec.model.UserRest;

/**
 * FactoryMethod for create object's Class UserRest
 */
public class UserRestFactory {
    public static UserRest createUser(String login, String password){
        UserRest userRest = new UserRest();
        userRest.setLogin(login);
        userRest.setPassword(password);
        return userRest;
    }
}
