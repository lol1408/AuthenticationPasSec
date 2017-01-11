package com.dante.passec.dao;

import com.dante.passec.model.ResourceData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Dao interface for UserRest extends JpaRepository
 */
public interface UserRestDao extends JpaRepository<ResourceData, Long> {

}
