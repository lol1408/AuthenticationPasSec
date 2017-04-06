package com.dante.passec.db.services;

import com.dante.passec.exception.ForbiddenException;
import com.dante.passec.model.ResourceData;
import com.dante.passec.model.UserRest;
import com.dante.passec.utils.ResourceDataManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


/**
 * Tests for DataService
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ResourceDataServiceTest extends Assert{

    private static final Logger logger = Logger.getLogger(ResourceDataServiceTest.class);

    UserRest user;
    List<ResourceData> resources;
    @Resource
    private EntityManagerFactory emf;
    private EntityManager em;
    @Resource
    private UserRestService userService;
    @Resource
    private ResourceDataService resourceDataService;

    @Before
    public void setUp(){
        em = emf.createEntityManager();
        resources = new ArrayList<>();

        //Коллекции с юзерами и ресурсами
        user = new UserRest("hello world", "world123", "mail", true);

        userService.addUser(user);
        resources.add(ResourceDataManager.createResourceData("mama", "I love you", user));

    }

    @After
    public void destroy(){
        List<ResourceData> allResources=null;
        try {
            allResources = resourceDataService.getResourcesByUser(user);
            for(ResourceData resource: allResources){
                resourceDataService.deleteResource(resource.getId());
            }
            userService.deleteUserByLogin("hello world");
        }catch (Exception ex){
            logger.info("oops");
        }

        user = null;
        resources = null;
    }

    //Первый добавленный ресурс в базе
    private ResourceData getFirstResource(){
        List<ResourceData> resources = resourceDataService.getResourcesByUser(user);
        return resources.get(0);
    }

    @Test
    public void insertShouldBeSuccess(){
        ResourceData resource = resources.get(0);
        resourceDataService.addResource(resource);
        ResourceData firstResource = getFirstResource();
        assertEquals(resource.getUrl(), firstResource.getUrl());
        assertEquals(resource.getPassword(), firstResource.getPassword());
        assertEquals(resource.getId(), firstResource.getId());
    }

    @Test
    public void deleteShouldBeSuccess(){
        ResourceData resource = resources.get(0);
        ResourceData save = resourceDataService.addResource(resource);
        resourceDataService.deleteResource(save.getId());
        assertNull(resourceDataService.getResourceById(save.getId()));
    }

    @Test
    public void updateShouldBeSuccess(){
        ResourceData resourceData = resourceDataService.addResource(resources.get(0));
        //Создаем копию которую поменяем
        ResourceData update = new ResourceData(resourceData);
        update.setUrl("Not hello");
        update.setPassword("Not password");
        resourceDataService.update(update);
        ResourceData firstResource = getFirstResource();

        /*Первоначальная запись не должна совпадать с обновленной
        совпадать должен долько Id*/
        assertEquals(resourceData.getId(), firstResource.getId());
        assertNotEquals(resourceData.getUrl(), firstResource.getUrl());
        assertNotEquals(resourceData.getPassword(),firstResource.getPassword());
        /*Первая запись в базе данных должна совпадать с объектом который
        сохранялся в базу*/
        assertEquals(update.getId(), firstResource.getId());
        assertEquals(update.getUrl(), firstResource.getUrl());
        assertEquals(update.getPassword(), firstResource.getPassword());

    }

    @Test
    public void selectAllResourcesShouldBeSuccess(){
        //Добавляем все в базу
        for(ResourceData resource:resources){
            resourceDataService.addResource(resource);
        }
        //Достаем из базы, заносим в новый лист
        List<ResourceData> resourcesTemp = resourceDataService.getResourcesByUser(user);
        //Сравниваем коллекции
        for(int i=0; i<resources.size(); i++){
            ResourceData inParentList = resources.get(i);
            ResourceData inBase = resources.get(i);

            assertEquals(inParentList.getId(), inBase.getId());
            assertEquals(inParentList.getUrl(), inBase.getUrl());
            assertEquals(inParentList.getPassword(), inBase.getPassword());
        }
    }

    @Test
    public void selectByIdShouldBeSuccess(){
        ResourceData resource = resourceDataService.addResource(resources.get(0));
        ResourceData resourceById = resourceDataService.getResourceById(resource.getId());

        assertEquals(resource.getId(), resourceById.getId());
        assertEquals(resource.getUrl(), resourceById.getUrl());
        assertEquals(resource.getPassword(), resourceById.getPassword());
    }

    @Test(expected = ForbiddenException.class)
    public void insertShouldThrowForbiddenException(){
        ResourceData resourceData = new ResourceData("url", "password", null);
        resourceDataService.addResource(resourceData);
    }

    @Test(expected = ForbiddenException.class)
    public void updateShouldThrowForbiddenException(){
        ResourceData resourceData = resources.get(0);
        resourceDataService.addResource(resourceData);
        ResourceData update = new ResourceData(resourceData);
        update.setUser(null);
        resourceDataService.update(update);
    }

    @Test(expected = ForbiddenException.class)
    public void deleteShouldThrowForbiddenException(){
        ResourceData resourceData = resources.get(0);
        resourceDataService.addResource(resourceData);
        resourceDataService.deleteResource(resourceData.getId()+1);
    }

    @Test(expected = ForbiddenException.class)
    public void getByIdShouldThrowForbiddenExceptin(){
        ResourceData resourceData = resources.get(0);
        resourceDataService.addResource(resourceData);
        resourceDataService.getResourceById(null);
    }

    @Test
    public void getAllShouldReturnListWithSize0(){
        List<ResourceData> resourceData = resourceDataService.getResourcesByUser(user);
        assertEquals(resourceData.size(), 0);
    }

    @Test(expected = ForbiddenException.class)
    public void getByUserIdShouldThrowForbiddenExceptin(){
        ResourceData resourceData = resources.get(0);
        resourceDataService.addResource(resourceData);
        resourceData.getUser().setId(null);
        resourceDataService.getResourceById(resourceData.getUser().getId());
    }

}
