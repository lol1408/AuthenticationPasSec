package com.dante.passec.rest;

import com.dante.passec.config.HibernateConfig;
import com.dante.passec.config.MainConfig;
import com.dante.passec.config.WebConfig;
import com.dante.passec.db.services.SessionService;
import com.dante.passec.db.services.UserRestService;
import com.dante.passec.excaption.ForbiddenExcepion;
import com.dante.passec.model.Session;
import com.dante.passec.model.UserRest;
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

import static com.dante.passec.utils.Converter.toJson;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
/**
 * Test class for restController
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, HibernateConfig.class, MainConfig.class}, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class TestUserRest {

    @Autowired
    WebApplicationContext mac;
    UserRest user;
    Session session;
    @InjectMocks
    private ControllerRestUser restUser;
    @Mock
    private UserRestService userService;
    @Mock
    private SessionService sessionService;
    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(restUser).build();/*webAppContextSetup(this.mac).dispatchOptions(true).build();*/
        user = new UserRest("Hello world", "123456");
        user.setId(1L);
        session = new Session(user);
    }

    @Test
    public void getUserCurrentUserShouldBeSuccess() throws Exception {
        when(sessionService.sessionIsActual(session.getToken())).thenReturn(user);

        mockMvc.perform(get("/users/")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("token", session.getToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.login", is("Hello world")))
                .andExpect(jsonPath("$.password", is("123456")));

        verify(sessionService, times(1)).sessionIsActual(session.getToken());
        verifyNoMoreInteractions(sessionService);
    }
    @Test
    public void getUserCurrentUserShouldThrowUnauthorizedException() throws Exception {
        when(sessionService.sessionIsActual(session.getToken())).thenReturn(user);

        mockMvc.perform(get("/users/")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header("token", 123123123))
                .andExpect(status().isUnauthorized())
                .andDo(print());

        verify(sessionService, times(1)).sessionIsActual(123123123);
        verifyNoMoreInteractions(sessionService);
    }
    @Test
    public void registrationShouldBeSuccess() throws Exception {
        when(userService.checkAlreadyExist(user.getLogin())).thenReturn(false);
        when(userService.addUser(user)).thenReturn(user);
        String json = toJson(user);
        mockMvc.perform(post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(content().
                        contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.login", is("Hello world")))
                .andExpect(jsonPath("$.password", is("123456")));

        verify(userService, times(1)).checkAlreadyExist(user.getLogin());
        verify(userService, times(1)).addUser(user);
        verifyNoMoreInteractions(userService);
    }
    @Test
    public void registrationShouldThrowForbiddenException() throws Exception {
        when(userService.checkAlreadyExist(user.getLogin())).thenReturn(false);
        when(userService.addUser(user)).thenThrow(ForbiddenExcepion.class);
        String json = toJson(user);
        mockMvc.perform(post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isForbidden());

        verify(userService, times(1)).checkAlreadyExist(user.getLogin());
        verify(userService, times(1)).addUser(user);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void updateShouldBeSuccess() throws Exception{
        when(sessionService.sessionIsActual(session.getToken())).thenReturn(user);
        UserRest newUser = new UserRest("new login", "new password");
        when(userService.updateUser(newUser)).thenReturn(newUser);
        newUser.setId(user.getId());
        String json = toJson(newUser);

        mockMvc.perform(put("/users/")
                .header("token", session.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(content().
                        contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.login", is("new login")))
                .andExpect(jsonPath("$.password", is("new password")));

        verify(sessionService, times(1)).
                sessionIsActual(session.getToken());
        verify(userService, times(1)).updateUser(user);
        verifyNoMoreInteractions(sessionService, userService);
    }
    @Test
    public void updateShouldThrowUnauthorizedException() throws Exception{
        when(sessionService.sessionIsActual(session.getToken())).thenReturn(user);
        UserRest newUser = new UserRest("new login", "new password");
        newUser.setId(user.getId());
        String json = toJson(newUser);

        mockMvc.perform(put("/users/")
                .header("token", 123123123)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isUnauthorized());

        verify(sessionService, times(1)).
                sessionIsActual(123123123);
        verifyNoMoreInteractions(sessionService, userService);
    }


    @Test
    public void deleteUserShouldBeSuccess() throws Exception {
        when(sessionService.sessionIsActual(session.getToken())).thenReturn(user);
        doNothing().when(userService).deleteUser(user.getId());

        mockMvc.perform(delete("/users/")
                .header("token", session.getToken()))
                .andExpect(status().isOk());

        verify(sessionService, times(1))
                .sessionIsActual(session.getToken());
        verify(userService, times(1)).
                deleteUser(user.getId());
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void deleteUserShouldThrowUnauthorizedException() throws Exception {
        when(sessionService.sessionIsActual(session.getToken())).thenReturn(user);
        doNothing().when(userService).deleteUser(user.getId());

        mockMvc.perform(delete("/users/")
                .header("token", 123123123))
                .andExpect(status().isUnauthorized());

        verify(sessionService, times(1))
                .sessionIsActual(123123123);
        verifyNoMoreInteractions(userService);
    }


}
