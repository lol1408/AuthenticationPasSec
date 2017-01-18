package com.dante.passec.dao;

import com.dante.passec.model.ResourceData;
import com.dante.passec.model.UserRest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Dao interface for UserRest extends JpaRepository
 */
public interface UserRestDao extends JpaRepository<UserRest, Long> {

    UserRest getUserByLogin(String login);

}
