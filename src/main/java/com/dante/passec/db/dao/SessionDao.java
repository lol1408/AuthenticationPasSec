package com.dante.passec.db.dao;

import com.dante.passec.model.Session;
import com.dante.passec.model.UserRest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Dao for Session
 * @author Dante de Braso
 * @version 1.0
 */
public interface SessionDao extends JpaRepository<Session, Long>{
    Session findByToken(String token);
}
