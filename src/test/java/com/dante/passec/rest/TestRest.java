package com.dante.passec.rest;

import com.dante.passec.config.WebConfig;
import com.dante.passec.configs.HibernateConfigT;
import com.dante.passec.model.UserRest;
import com.dante.passec.services.UserRestService;
import com.dante.passec.utils.UserRestManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Test class for restController
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, HibernateConfigT.class}, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class TestRest {

    private MockMvc mockMvc;

    @Test
    public void findAll() throws Exception{
        UserRest user = UserRestManager.createUser("Hello world", "123456");
        UserRest user2 = UserRestManager.createUser("By world", "123456");
        UserRestService service = mock(UserRestService.class);
        service.addUser(user);
        service.addUser(user2);

        when(service.allUsers()).thenReturn(Arrays.asList(user, user2));


        mockMvc.perform(get("/restusers/list")).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

}
