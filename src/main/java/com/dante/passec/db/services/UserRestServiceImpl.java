package com.dante.passec.db.services;

import com.dante.passec.db.dao.UserRestDao;
import com.dante.passec.exception.EmailIsBusyException;
import com.dante.passec.exception.ForbiddenException;
import com.dante.passec.exception.LoginIsBusyException;
import com.dante.passec.model.UserRest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implements for UserRestService interface
 * @author MakarenkoSergey
 * @version 1.0
 */
@Service
public class UserRestServiceImpl implements UserRestService {
    private static final Logger logger = Logger.getLogger(UserRestService.class);
    @Autowired
    UserRestDao userRestDao;

    public UserRest userByLogin(String login) {
        return userRestDao.getUserByLogin(login);
    }

    public UserRest userByMail(String mail) {
        return userRestDao.getUserByMail(mail);
    }

    public UserRest userByLoginOrMail(String login, String mail) {
        return userRestDao.getUserByLoginOrMail(login, mail);
    }

    public UserRest userById(Long id) {
        return userRestDao.findOne(id);
    }

    public UserRest addUser(UserRest user) {
        UserRest tempUser = new UserRest(user);
        try {
            userRestDao.saveAndFlush(tempUser);
        }catch (Exception ex){
            throw new ForbiddenException();
        }
        user.setId(tempUser.getId());
        return tempUser;
    }

    public UserRest updateUser(UserRest user) {
        UserRest tempUser = new UserRest(user);
        try {
            return userRestDao.saveAndFlush(tempUser);
        }catch (Exception ex){
            throw new ForbiddenException();
        }
    }

    public void deleteUser(Long id) {
        try {
            userRestDao.delete(id);
        }catch (Exception ex){
            throw new ForbiddenException();
        }
    }

    public void deleteUserByLogin(String login) {
        userRestDao.deleteUserByLogin(login);
    }

    public boolean authentication(String login, String password) {
        UserRest user = userRestDao.getUserByLogin(login);
        if(user==null)
            user = userRestDao.getUserByMail(login);
        if(user==null)
            return false;
        else
            return user.getPassword().equals(password);
    }
    public boolean checkAlreadyExist(String login, String mail){
        if(userRestDao.getUserByLogin(login)!=null)
            throw new LoginIsBusyException();
        else if(userRestDao.getUserByMail(mail)!=null)
            throw new EmailIsBusyException();
        else return true;
    }
}
