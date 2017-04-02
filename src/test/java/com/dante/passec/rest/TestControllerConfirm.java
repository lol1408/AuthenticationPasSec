package com.dante.passec.rest;


import com.dante.passec.db.services.UserRestService;
import com.dante.passec.mail.MailService;
import com.dante.passec.mail.RandomTokenService;
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
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
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
public class TestControllerConfirm {

    UserRest user;

    @InjectMocks
    private ControllerConfirm authentication;
    @Mock
    private UserRestService userService;
    @Mock
    private MailService mailService;
    @Mock
    private RandomTokenService randomTokenService;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void beforeMethod(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(authentication).build();
        user = new UserRest("login1", "password1", "sergey.king96@mail.ru", true);
    }
    @Test
    public void confirmAccountShouldBeSuccess(){
        when(userService.userByMail(user.getMail())).thenReturn(user);
    }

}
