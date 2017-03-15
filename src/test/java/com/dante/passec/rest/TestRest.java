package com.dante.passec.rest;

import com.dante.passec.config.HibernateConfig;
import com.dante.passec.config.MainConfig;
import com.dante.passec.config.WebConfig;
import com.dante.passec.model.UserRest;
import com.dante.passec.db.services.UserRestService;
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

import static org.hamcrest.core.Is.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static com.dante.passec.utils.Converter.*;
/**
 * Test class for restController
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, HibernateConfig.class, MainConfig.class}, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class TestRest {

    @InjectMocks
    private ControllerRestUser restUser;

    @Mock
    private UserRestService userRestService;

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext mac;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(restUser).build();/*webAppContextSetup(this.mac).dispatchOptions(true).build();*/
    }
    @Test
    public void findAll_should_be_success() throws Exception{
        UserRest user = UserRestManager.createUser("Hello world", "123456");
        UserRest user2 = UserRestManager.createUser("By world", "123456");
        user.setId(1L); user2.setId(2L);
        when(userRestService.allUsers()).thenReturn(Arrays.asList(user, user2));
        mockMvc.perform(get("/restusers/").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
                andDo(print()).
                andExpect(jsonPath("$[0].id", is(1))).
                andExpect(jsonPath("$[0].login",is("Hello world"))).
                andExpect(jsonPath("$[0].password", is("123456"))).
                andExpect(jsonPath("$[1].id", is(2))).
                andExpect(jsonPath("$[1].login", is("By world"))).
                andExpect(jsonPath("$[1].password", is("123456")));
        verify(userRestService, times(1)).allUsers();
        verifyNoMoreInteractions(userRestService);
    }
    @Test
    public void findById_should_be_success() throws Exception {
        UserRest user = UserRestManager.createUser("Hello world", "123456");
        user.setId(1L);
        when(userRestService.userById(user.getId())).thenReturn(user);
        mockMvc.perform(get("/restusers/{id}", user.getId()).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
                andDo(print()).
                andExpect(jsonPath("$.id", is(1))).
                andExpect(jsonPath("$.login", is("Hello world"))).
                andExpect(jsonPath("$.password", is("123456")));
        verify(userRestService, times(1)).userById(1L);
        verifyNoMoreInteractions(userRestService);
    }
    @Test
    public void addUser_should_be_success() throws Exception {
        UserRest user = UserRestManager.createUser("My heppy day", "oneoneone");
        String json = toJson(user);
        mockMvc.perform(post("/restusers/").contentType(MediaType.APPLICATION_JSON).
        content(json)).andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
                andDo(print()).
                andExpect(jsonPath("$.msg", is("Пользователь успешно добавлен"))).
                andExpect(jsonPath("$.code", is("200")));
        verify(userRestService, times(1)).addUser(user);
        verifyNoMoreInteractions(userRestService);
    }
    @Test
    public void deleteUserById_should_be_success() throws Exception {
        UserRest user = UserRestManager.createUser("jobMyJob", "lalalend");
        user.setId(1L);
        doNothing().when(userRestService).deleteUser(user.getId());
        mockMvc.perform(delete("/restusers/{id}", user.getId())).
                andExpect(status().isOk());
        verify(userRestService, times(1)).deleteUser(1L);
        verifyNoMoreInteractions(userRestService);
    }
    @Test
    public void update_should_be_success() throws Exception{
        UserRest user = UserRestManager.createUser("Hello world", "123456");
        user.setId(1L);
        String json = toJson(user);
        mockMvc.perform(put("/restusers/").contentType(MediaType.APPLICATION_JSON).
        content(json)).andExpect(status().isOk());
        verify(userRestService, times(1)).updateUser(user);
        verifyNoMoreInteractions(userRestService);
    }
}
