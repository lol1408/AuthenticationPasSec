package com.dante.passec.db.services;

import com.dante.passec.model.UserRest;

import java.util.List;

/**
 * service for model UserData
 */
public interface UserRestService {

    UserRest userByLogin(String login);
    UserRest userByMail(String mail);
    UserRest userByLoginOrMail(String login, String mail);
    UserRest userById(Long id);
    UserRest addUser(UserRest user);
    UserRest updateUser(UserRest user);
    void deleteUser(Long id);
    void deleteUserByLogin(String login);
    boolean authentication(String login, String  password);
    boolean checkAlreadyExist(String login, String mail);
}
