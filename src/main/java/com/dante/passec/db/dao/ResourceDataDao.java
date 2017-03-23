package com.dante.passec.db.dao;

import com.dante.passec.model.ResourceData;
import com.dante.passec.model.UserRest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Dao interface for UserRest implements JpaRepository
 */
public interface ResourceDataDao extends JpaRepository<ResourceData, Long>{
    @Transactional(readOnly = true)
    List<ResourceData> getResourcesByUserId(Long id);

//    @Query("select c from ResourceData c join c.user a where (a.id = :user_id)")
//    List<ResourceData> getResourceByUserId(@Param("user_id")Long id);

}
