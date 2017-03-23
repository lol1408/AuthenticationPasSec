package com.dante.passec.db.dao;

import com.dante.passec.model.ResourceData;
import com.dante.passec.model.UserRest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Dao interface for UserRest implements JpaRepository
 */
public interface ResourceDataDao extends JpaRepository<ResourceData, Long>{

    List<ResourceData> getResourcesByUser(UserRest user);


}
