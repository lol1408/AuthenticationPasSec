package com.dante.passec.rest;

import com.dante.passec.config.WebConfig;
import com.dante.passec.configs.HibernateConfigT;
import com.dante.passec.model.ResourceData;
import com.dante.passec.model.UserRest;
import com.dante.passec.db.services.ResourceDataService;
import com.dante.passec.db.services.UserRestService;
import com.dante.passec.utils.ResourceDataManager;
import com.dante.passec.utils.UserRestManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.hamcrest.core.Is.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static com.dante.passec.utils.Converter.*;
/**
 * Test class for restController
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, HibernateConfigT.class}, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class TestResourceRest {
    @InjectMocks
    private ControllerResourceData controllerResource;

    @Mock
    private UserRestService userRestService;
    @Mock
    private ResourceDataService resourceDataService;


    private MockMvc mockMvc;
    private UserRest userRest;
    private ResourceData resource1;
    private ResourceData resource2;
    List<ResourceData> resources;

    @Autowired
    WebApplicationContext mac;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(controllerResource).build();/*webAppContextSetup(this.mac).dispatchOptions(true).build();*/
        userRest = UserRestManager.createUser("Hello world", "password");
        userRest.setId(1L);
        resource1 = ResourceDataManager.createResourceData("login1", "password1", userRest);
        resource2 = ResourceDataManager.createResourceData("login2", "password2", userRest);
        resource1.setId(1L);
        resource2.setId(2L);
        resources = Arrays.asList(resource1, resource2);
    }

    @Test
    public void findAllResourcesByUser_should_be_success() throws Exception {
        when(userRestService.userById(userRest.getId())).thenReturn(userRest);
        when(resourceDataService.getResourcesByUser(userRest)).thenReturn(resources);
        mockMvc.perform(get("/resource/", userRest.getId())).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andDo(print()).
                andExpect(jsonPath("$[0].id", is(1))).
                andExpect(jsonPath("$[0].login", is("login1"))).
                andExpect(jsonPath("$[0].password", is("password1"))).
                andExpect(jsonPath("$[1].id", is(2))).
        andExpect(jsonPath("$[1].login", is("login2"))).
        andExpect(jsonPath("$[1].password", is("password2")));
        verify(userRestService, times(1)).userById(userRest.getId());
        verify(resourceDataService, times(1)).getResourcesByUser(userRest);
        verifyNoMoreInteractions(userRestService, resourceDataService);
    }
    @Test
    public void findResourceById_should_be_success() throws Exception{
        when(resourceDataService.getResourceById(resource1.getId())).thenReturn(resource1);
        mockMvc.perform(get("/resource/{id}", resource1.getId())).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
                andDo(print()).
                andExpect(jsonPath("$.id", is(1))).
                andExpect(jsonPath("$.login", is("login1"))).
                andExpect(jsonPath("$.password", is("password1")));
        verify(resourceDataService, times(1)).getResourceById(resource1.getId());
        verifyNoMoreInteractions(resourceDataService);
    }
    @Test
    public void addResource_should_be_success() throws Exception{
        resource1.setId(null);
        when(userRestService.userById(userRest.getId())).thenReturn(userRest);
        String json = toJson(resource1);
        mockMvc.perform(post("/resource/{id}", userRest.getId()).contentType(MediaType.APPLICATION_JSON).
        content(json)).andExpect(status().isOk()).andDo(print()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("$.code", is("200")));
        verify(userRestService, times(1)).userById(userRest.getId());
        verify(resourceDataService, times(1)).addResource(resource1);
        verifyNoMoreInteractions(userRestService, resourceDataService);
    }

    @Test
    public void updateResource_should_be_success() throws Exception{
        when(userRestService.userById(userRest.getId())).thenReturn(userRest);
        resource1.setId(1L);
        String json = toJson(resource1);
        doNothing().when(resourceDataService).update(resource1);
        mockMvc.perform(put("/resource/{id}", userRest.getId()).contentType(MediaType.APPLICATION_JSON).
                content(json)).andExpect(status().isOk()).andDo(print()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("$.code", is("200")));
        verify(userRestService, times(1)).userById(userRest.getId());
        verify(resourceDataService, times(1)).update(resource1);
        verifyNoMoreInteractions(userRestService, resourceDataService);
    }

}
