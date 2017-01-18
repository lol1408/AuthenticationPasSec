package com.dante.passec.services;

import com.dante.passec.model.UserRest;

import java.util.List;

/**
 * service for model UserData
 */
public interface UserRestService {

    List<UserRest> allUsers();
    UserRest userByLogin(String login);
    UserRest userById(Long id);
    UserRest addUser(UserRest user);
    UserRest updateUser(UserRest user);
    void deleteUser(Long id);

}
