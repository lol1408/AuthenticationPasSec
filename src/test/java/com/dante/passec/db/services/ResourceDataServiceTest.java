package com.dante.passec.db.services;

import com.dante.passec.config.HibernateConfig;
import com.dante.passec.configs.HibernateConfigT;
import com.dante.passec.model.ResourceData;
import com.dante.passec.model.UserRest;
import com.dante.passec.utils.ResourceDataManager;
import com.dante.passec.utils.UserRestManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

/**
 * Tests for DataService
 */
@DirtiesContext(classMode = AFTER_CLASS)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class})
public class ResourceDataServiceTest extends Assert{
    @Resource
    private EntityManagerFactory emf;
    private EntityManager em;

    @Resource
    private UserRestService userService;
    @Resource
    private ResourceDataService resourceDataService;

    List<UserRest> userRests;
    List<ResourceData> resourceDatas;

    @Before
    public void setUp(){
        em = emf.createEntityManager();
        userRests = new ArrayList<>();
        resourceDatas = new ArrayList<>();
        userRests.add(UserRestManager.createUser("hello world", "worldes"));
        resourceDatas.add(ResourceDataManager.createResourceData("mama", "I love you", userRests.get(0)));
    }

    @Test
    public void insertTest(){
        userService.addUser(userRests.get(0));
        resourceDatas.get(0).setUser(userService.userByLogin("hello world"));
        System.out.println(resourceDatas.get(0));
        resourceDataService.addResource(resourceDatas.get(0));
    }
    @Test
    public void deleteTest(){
        userService.addUser(userRests.get(0));
        UserRest user = userService.userByLogin("hello world");
        resourceDatas.get(0).setUser(user);
        resourceDataService.addResource(resourceDatas.get(0));
        List<ResourceData> resourcesByUser = resourceDataService.getResourcesByUserId(user.getId());
        resourceDataService.deleteResource(resourcesByUser.get(0).getId());
    }


    @After
    public void destroy(){
        userRests = null;
        resourceDatas = null;
    }

}
