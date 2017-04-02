package com.dante.passec.db.services;

import com.dante.passec.model.ResourceData;
import com.dante.passec.model.UserRest;
import com.dante.passec.utils.ResourceDataManager;
import com.dante.passec.utils.UserRestManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;


/**
 * Tests for DataService
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceDataServiceTest extends Assert{
    List<UserRest> users;
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
        users = new ArrayList<>();
        resources = new ArrayList<>();

        //Коллекции с юзерами и ресурсами
        users.add(UserRestManager.createUser("hello world", "world123", "mail"));
        resources.add(ResourceDataManager.createResourceData("mama", "I love you", users.get(0)));

        userService.addUser(users.get(0));

    }
    //Первый юзер в базе
    private UserRest getFirstUser(){
        List<UserRest> users = userService.allUsers();
        return users.get(0);
    }

    //Первый добавленный ресурс в базе
    private ResourceData getFirstResource(){
        List<ResourceData> resources = resourceDataService.allResources();
        return resources.get(0);
    }

    @Test
    public void insertShouldBeSuccess(){
        ResourceData resource = resources.get(0);
        resourceDataService.addResource(resource);
        ResourceData firstResource = getFirstResource();
        assertEquals(resource.getLogin(), firstResource.getLogin());
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
        update.setLogin("Not hello");
        update.setPassword("Not password");
        resourceDataService.update(update);
        ResourceData firstResource = getFirstResource();

        /*Первоначальная запись не должна совпадать с обновленной
        совпадать должен долько Id*/
        assertEquals(resourceData.getId(), firstResource.getId());
        assertNotEquals(resourceData.getLogin(), firstResource.getLogin());
        assertNotEquals(resourceData.getPassword(),firstResource.getPassword());
        /*Первая запись в базе данных должна совпадать с объектом который
        сохранялся в базу*/
        assertEquals(update.getId(), firstResource.getId());
        assertEquals(update.getLogin(), firstResource.getLogin());
        assertEquals(update.getPassword(), firstResource.getPassword());

    }

    @Test
    public void selectAllResourcesShouldBeSuccess(){
        //Добавляем все в базу
        for(ResourceData resource:resources){
            resourceDataService.addResource(resource);
        }
        //Достаем из базы, заносим в новый лист
        List<ResourceData> resourcesTemp = resourceDataService.allResources();
        //Сравниваем коллекции
        for(int i=0; i<resources.size(); i++){
            ResourceData inParentList = resources.get(i);
            ResourceData inBase = resources.get(i);

            assertEquals(inParentList.getId(), inBase.getId());
            assertEquals(inParentList.getLogin(), inBase.getLogin());
            assertEquals(inParentList.getPassword(), inBase.getPassword());
        }
    }

    @Test
    public void selectByIdShouldBeSuccess(){
        ResourceData resource = resourceDataService.addResource(resources.get(0));
        ResourceData resourceById = resourceDataService.getResourceById(resource.getId());

        assertEquals(resource.getId(), resourceById.getId());
        assertEquals(resource.getLogin(), resourceById.getLogin());
        assertEquals(resource.getPassword(), resourceById.getPassword());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void insertShouldThrowJpaSystemException(){
        ResourceData resourceData = new ResourceData("login", "password", null);
        resourceDataService.addResource(resourceData);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void updateShouldThrowJpaSystemException(){
        ResourceData resourceData = resources.get(0);
        resourceDataService.addResource(resourceData);
        ResourceData update = new ResourceData(resourceData);
        update.setUser(null);
        resourceDataService.update(update);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteShouldThrowJpaSystemException(){
        ResourceData resourceData = resources.get(0);
        resourceDataService.addResource(resourceData);
        resourceDataService.deleteResource(resourceData.getId()+1);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void getByIdShouldThrowJpaSystemException(){
        ResourceData resourceData = resources.get(0);
        resourceDataService.addResource(resourceData);
        resourceDataService.getResourceById(null);
    }

    @Test
    public void getAllShouldReturnListWithSize0(){
        List<ResourceData> resourceDatas = resourceDataService.allResources();
        assertEquals(resourceDatas.size(), 0);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void getByUserIdShouldThrowJpaSystemException(){
        ResourceData resourceData = resources.get(0);
        resourceDataService.addResource(resourceData);
        resourceData.getUser().setId(null);
        resourceDataService.getResourceById(resourceData.getUser().getId());
    }


    @After
    public void destroy(){
        List<UserRest> allUsers = userService.allUsers();
        List<ResourceData> allResources = resourceDataService.allResources();

        for(ResourceData resource: allResources){
            resourceDataService.deleteResource(resource.getId());
        }

        for(UserRest user: allUsers){
            userService.deleteUser(user.getId());
        }

        users = null;
        resources = null;
    }

}
