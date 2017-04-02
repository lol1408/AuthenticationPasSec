package com.dante.passec.db.services;

import com.dante.passec.db.dao.UserRestDao;
import com.dante.passec.model.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implements for UserRestService interface
 * @author MakarenkoSergey
 * @version 1.0
 */
@Service
public class UserRestServiceImpl implements UserRestService {

    @Autowired
    UserRestDao userRestDao;

    public List<UserRest> allUsers() {
        return userRestDao.findAll();
    }

    public UserRest userByLogin(String login) {
        return userRestDao.getUserByLogin(login);
    }

    public UserRest userByMail(String mail) {
        return userRestDao.getUserByMail(mail);
    }

    public UserRest userById(Long id) {
        return userRestDao.findOne(id);
    }

    public UserRest addUser(UserRest user) {
        UserRest tempUser = new UserRest(user);
        userRestDao.saveAndFlush(tempUser);
        user.setId(tempUser.getId());
        return user;
    }

    public UserRest updateUser(UserRest user) {
        UserRest tempUser = new UserRest(user);
        return userRestDao.saveAndFlush(tempUser);
    }

    public void deleteUser(Long id) {
        userRestDao.delete(id);
    }

    public boolean userIsReal(String login, String password) {
        UserRest user = userRestDao.getUserByLogin(login);
        if(user==null)
            user = userRestDao.getUserByMail(login);
        if(user==null)
            return false;
        else
            return user.getPassword().equals(password);
    }
    public int checkAlreadyExist(String login, String mail){
        if(userRestDao.getUserByLogin(login)!=null)
            return 1;
        else if(userRestDao.getUserByMail(mail)!=null)
            return 2;
        else return 3;
    }
}
