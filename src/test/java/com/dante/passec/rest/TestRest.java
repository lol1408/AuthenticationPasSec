package com.dante.passec.rest;

import com.dante.passec.config.WebConfig;
import com.dante.passec.configs.HibernateConfigT;
import com.dante.passec.model.UserRest;
import com.dante.passec.services.UserRestService;
import com.dante.passec.utils.UserRestManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Test class for restController
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, HibernateConfigT.class}, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class TestRest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext mac;

    @Before
    public void setup(){
        this.mockMvc = webAppContextSetup(this.mac).dispatchOptions(true).build();
    }
    @Test
    public void findAll() throws Exception{
        UserRest user = UserRestManager.createUser("Hello world", "123456");
        UserRest user2 = UserRestManager.createUser("By world", "123456");
        user.setId(1L); user2.setId(2L);
        UserRestService service = mock(UserRestService.class);

        when(service.allUsers()).thenReturn(Arrays.asList(user, user2));

        mockMvc.perform(get("/restusers/list")).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).
                andExpect(jsonPath("$[0].id", is(1L))).
                andExpect(jsonPath("$[0].login", is("Hello world"))).
                andExpect(jsonPath("$[0].password", is(123456))).
                andExpect(jsonPath("$[1].id", is(2L))).
                andExpect(jsonPath("$[1].login", is("By world"))).
                andExpect(jsonPath("$[1].password", is(123456)));
    }

}
