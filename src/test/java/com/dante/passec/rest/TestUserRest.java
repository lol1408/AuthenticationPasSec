package com.dante.passec.rest;

import com.dante.passec.db.services.SessionService;
import com.dante.passec.db.services.UserRestService;
import com.dante.passec.exception.ForbiddenException;
import com.dante.passec.exception.UnauthorizedException;
import com.dante.passec.mail.MailService;
import com.dante.passec.mail.RandomTokenService;
import com.dante.passec.model.Session;
import com.dante.passec.model.UserRest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static com.dante.passec.utils.Converter.toJson;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
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
@RunWith(SpringRunner.class)
@SpringBootTest
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
    @Mock
    private MailService mailService;
    @Mock
    private RandomTokenService randomTokenService;

    private MockMvc mockMvc;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(restUser).build();/*webAppContextSetup(this.mac).dispatchOptions(true).build();*/
        user = new UserRest("Hello world", "123456", "sergey.king96@mail.ru", true);
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
                .andExpect(jsonPath("$.password", is("123456")))
                .andExpect(jsonPath("$.mail", is("sergey.king96@mail.ru")))
                .andExpect(jsonPath("$.active", is(true)));

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
        when(userService.checkAlreadyExist(user.getLogin(), user.getMail())).thenReturn(true);
        user.setActive(false);
        when(userService.addUser(user)).thenReturn(user);
        when(randomTokenService.getRandomToken(user.getMail())).thenReturn(12345);
        when(mailService.sendMail(12345, user.getLogin(), user.getMail())).thenReturn(true);
        String json = toJson(user);
        mockMvc.perform(post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().isOk());

        verify(userService, times(1)).checkAlreadyExist(user.getLogin(), user.getMail());
        verify(userService, times(1)).addUser(user);
        verify(randomTokenService, times(1)).getRandomToken(user.getMail());
        verify(mailService, times(1)).sendMail(12345, user.getLogin(), user.getMail());
        verifyNoMoreInteractions(userService);
    }
    @Test
    public void registrationShouldThrowForbiddenException() throws Exception {
        when(userService.checkAlreadyExist(user.getLogin(), user.getMail())).thenReturn(true);
        when(userService.addUser(user)).thenThrow(ForbiddenException.class);
        String json = toJson(user);
        mockMvc.perform(post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isForbidden());

        verify(userService, times(1)).checkAlreadyExist(user.getLogin(), user.getMail());
        verify(userService, times(1)).addUser(user);
        verifyNoMoreInteractions(userService);
    }
    @Test
    public void updateShouldBeSuccess() throws Exception{
        when(sessionService.sessionIsActual(session.getToken())).thenReturn(user);
        UserRest newUser = new UserRest(user);
        when(userService.updateUser(newUser)).thenReturn(newUser);
        newUser.setPassword("new password");

        mockMvc.perform(put("/users/password")
                .header("token", session.getToken())
                .header("oldpassword", user.getPassword())
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUser.getPassword()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.login", is("Hello world")))
                .andExpect(jsonPath("$.password", is("new password")));

        verify(sessionService, times(1)).
                sessionIsActual(session.getToken());
        verify(userService, times(1)).updateUser(user);
        verifyNoMoreInteractions(sessionService, userService);
    }
    @Test
    public void updatePasswordThrowUnauthorizedException() throws Exception{
        when(sessionService.sessionIsActual(session.getToken())).thenThrow(UnauthorizedException.class);
        UserRest userRest = new UserRest(user);
        userRest.setPassword("new password");
        String json = toJson(userRest);

        mockMvc.perform(put("/users/password")
                .header("token", session.getToken())
                .header("oldpassword", user.getPassword())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isUnauthorized());

        verify(sessionService, times(1)).
                sessionIsActual(session.getToken());
        verifyNoMoreInteractions(sessionService, userService);
    }

}
