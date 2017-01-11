package com.dante.passec.dao;

import com.dante.passec.model.UserRest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Dao interface for UserRest implements JpaRepository
 */
public interface ResourceDataDao extends JpaRepository<UserRest, Long>{
    
}
