package com.dante.passec.db.dao;

import com.dante.passec.model.UserRest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Dao interface for UserRest extends JpaRepository
 */
public interface UserRestDao extends JpaRepository<UserRest, Long> {

    UserRest getUserByLogin(String login);
    UserRest getUserByMail(String mail);
}
