package com.dante.passec.db.dao;

import com.dante.passec.model.ResourceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Dao interface for UserRest implements JpaRepository
 */
public interface ResourceDataDao extends JpaRepository<ResourceData, Long>{
    @Transactional(readOnly = true)
    List<ResourceData> getResourcesByUserId(Long id);
}
