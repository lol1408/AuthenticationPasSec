package com.dante.passec.dao;

import com.dante.passec.model.ResourceData;
import com.dante.passec.model.UserRest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Dao interface for UserRest implements JpaRepository
 */
public interface ResourceDataDao extends JpaRepository<ResourceData, Long>{

    List<ResourceData> getResourcesByUser(UserRest user);


}
