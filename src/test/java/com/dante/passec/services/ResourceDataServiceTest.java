package com.dante.passec.services;

import com.dante.passec.configs.HibernateConfigT;
import com.dante.passec.model.ResourceData;
import com.dante.passec.model.UserRest;
import com.dante.passec.utils.UserRestManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for DataService
 */
@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfigT.class})
@Transactional
public class ResourceDataServiceTest extends Assert{
    @Resource
    private EntityManagerFactory emf;
    private EntityManager em;

    @Resource
    private UserRestService userService;

    List<UserRest> userRests;
    List<ResourceData> resourceDatas;

    @Before
    public void setUp(){
        em = emf.createEntityManager();
        userRests = new ArrayList<>();
        userRests.add(UserRestManager.createUser("hello world", "worldes"));
    }

}
