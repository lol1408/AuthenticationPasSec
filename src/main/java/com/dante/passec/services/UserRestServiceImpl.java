package com.dante.passec.services;

import com.dante.passec.dao.UserRestDao;
import com.dante.passec.model.UserRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public UserRest userById(Long id) {
        return userRestDao.findOne(id);
    }

    public UserRest addUser(UserRest user) {
        return userRestDao.save(user);
    }

    public UserRest updateUser(UserRest user) {
        return userRestDao.saveAndFlush(user);
    }

    public void deleteUser(Long id) {
        userRestDao.delete(id);
    }

    public boolean userIsReal(String login, String password) {
        UserRest user = userRestDao.getUserByLogin(login);
        return user.getPassword().equals(password);
    }
}
