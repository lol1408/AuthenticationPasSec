package com.dante.passec.rest;

import com.dante.passec.db.services.SessionService;
import com.dante.passec.db.services.UserRestService;
import com.dante.passec.model.Session;
import com.dante.passec.model.UserRest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.hamcrest.core.Is.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class TestAuth {

    @InjectMocks
    private ControllerAuthentication authentication;
    @Mock
    private UserRestService userService;
    @Mock
    private SessionService sessionService;
    @Autowired
    private MockMvc mockMvc;
    private UserRest user;
    private Session session;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(authentication).build();
        user = new UserRest("login", "password");
        session = new Session(user);
    }

    @Test
    public void loginWillBeSuccess() throws Exception {
        when(userService.userIsReal(user.getLogin(), user.getPassword())).thenReturn(true);
        when(userService.userByLogin("login")).thenReturn(user);
        when(sessionService.addSession(user)).thenReturn(session);

        mockMvc.perform(get("/login")
                .header("login", user.getLogin())
                .header("password", user.getPassword()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(jsonPath("$", is(session.getToken())));

        verify(userService, times(1)).userIsReal(user.getLogin(), user.getPassword());
        verify(userService, times(1)).userByLogin("login");
        verify(sessionService, times(1)).addSession(user);
        verifyNoMoreInteractions(userService, sessionService);

    }

    @Test
    public void loginWillThrowUserNotFoundException() throws Exception {
        when(userService.userIsReal(user.getLogin(), user.getPassword())).thenReturn(true);

        mockMvc.perform(get("/login")
                .header("login", "not login")
                .header("password", user.getPassword()))
                .andExpect(status().isUnauthorized())
                .andDo(print());

        verify(userService, times(1)).userIsReal("not login", user.getPassword());
        verifyNoMoreInteractions(userService, sessionService);
    }
    @Test
    public void logoutWillBeSuccess() throws Exception {
        when(sessionService.sessionIsActual(session.getToken())).thenReturn(user);
        when(sessionService.findByToken(session.getToken())).thenReturn(session);
        when(sessionService.setNotIncluding(session)).thenReturn(session);

        mockMvc.perform(get("/logout")
                .header("token", session.getToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print());

        verify(sessionService, times(1)).sessionIsActual(session.getToken());
        verify(sessionService, times(1)).findByToken(session.getToken());
        verify(sessionService, times(1)).setNotIncluding(session);
        verifyNoMoreInteractions(sessionService);
    }
    @Test
    public void logoutWillThrowUnauthorizedException() throws Exception {
        when(sessionService.sessionIsActual(session.getToken())).thenReturn(user);

        mockMvc.perform(get("/logout")
                .header("token", 123123123))
                .andExpect(status().isUnauthorized())
                .andDo(print());

        verify(sessionService, times(1)).sessionIsActual(123123123);
        verifyNoMoreInteractions(sessionService);
    }


}
