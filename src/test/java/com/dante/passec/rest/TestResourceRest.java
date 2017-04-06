package com.dante.passec.rest;

import com.dante.passec.db.services.SessionService;
import com.dante.passec.exception.UnauthorizedException;
import com.dante.passec.model.ResourceData;
import com.dante.passec.model.Session;
import com.dante.passec.model.UserRest;
import com.dante.passec.db.services.ResourceDataService;
import com.dante.passec.db.services.UserRestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;
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
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class TestResourceRest {
    List<ResourceData> resources;
    @Autowired
    WebApplicationContext mac;
    @InjectMocks
    private ControllerResourceData controllerResource;
    @Mock
    private UserRestService userService;
    @Mock
    private ResourceDataService resourceService;
    @Mock
    private SessionService sessionService;
    @Autowired
    private MockMvc mockMvc;
    private UserRest userRest;
    private ResourceData resource1;
    private ResourceData resource2;
    private Session session;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(controllerResource).build();/*webAppContextSetup(this.mac).dispatchOptions(true).build();*/
        userRest = new UserRest("Hello world", "password", "mail", true);
        userRest.setId(1L);
        resource1 = new ResourceData("url", "password", userRest);
        resource2 = new ResourceData("url2", "password2", userRest);
        resource1.setId(1L);
        resource2.setId(2L);
        resources = Arrays.asList(resource1, resource2);
        session = new Session(userRest);
    }

    @Test
    public void getResourceByUserShouldBeSuccess() throws Exception {
        when(sessionService.sessionIsActual(session.getToken())).thenReturn(userRest);
        when(resourceService.getResourcesByUser(userRest)).thenReturn(resources);

        mockMvc.perform(get("/resources/")
                .header("token", session.getToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].url", is("url")))
                .andExpect(jsonPath("$[0].password", is("password")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].url", is("url2")))
                .andExpect(jsonPath("$[1].password", is("password2")));

        verify(sessionService, times(1)).sessionIsActual(session.getToken());
        verify(resourceService, times(1)).getResourcesByUser(userRest);
        verifyNoMoreInteractions(sessionService, resourceService);
    }

    @Test
    public void getResourceByUserShouldThrowUnauthorizedException() throws Exception {
        when(sessionService.sessionIsActual(session.getToken())).thenThrow(UnauthorizedException.class);

        mockMvc.perform(get("/resources/")
                .header("token", session.getToken()))
                .andExpect(status().isUnauthorized())
                .andDo(print());

        verify(sessionService, times(1)).sessionIsActual(session.getToken());
        verifyNoMoreInteractions(sessionService);
    }

    @Test
    public void saveResourceShouldBeSuccess() throws Exception{
        when(sessionService.sessionIsActual(session.getToken())).thenReturn(userRest);
        when(resourceService.addResource(resource1)).thenReturn(resource1);

        String json = toJson(resource1);
        mockMvc.perform(post("/resources/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header("token", session.getToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.url", is("url")))
                .andExpect(jsonPath("$.password", is("password")));

        verify(sessionService, times(1))
                .sessionIsActual(session.getToken());
        verify(resourceService, times(1))
                .addResource(resource1);
        verifyNoMoreInteractions(sessionService, resourceService);
    }
    @Test
    public void saveResourceShouldThrowUnauthorizedException() throws Exception {
        when(sessionService.sessionIsActual(session.getToken())).thenThrow(UnauthorizedException.class);

        String json = toJson(resource1);
        mockMvc.perform(post("/resources/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header("token", session.getToken()))
                .andExpect(status().isUnauthorized())
                .andDo(print());

        verify(sessionService, times(1))
                .sessionIsActual(session.getToken());
        verifyNoMoreInteractions(sessionService, resourceService);
    }

    @Test
    public void changeResourceShouldBeSuccess() throws Exception{
        when(sessionService.sessionIsActual(session.getToken())).thenReturn(userRest);
        when(resourceService.update(resource1)).thenReturn(resource1);
        String json = toJson(resource1);

        mockMvc.perform(put("/resources/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header("token", session.getToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.url", is("url")))
                .andExpect(jsonPath("$.password", is("password")));

        verify(sessionService, times(1))
                .sessionIsActual(session.getToken());
        verify(resourceService, times(1)).update(resource1);
        verifyNoMoreInteractions(sessionService, resourceService);
    }
    @Test
    public void changeResourceShouldThrowUnauthorizedException() throws Exception {
        when(sessionService.sessionIsActual(session.getToken())).thenThrow(UnauthorizedException.class);
        String json = toJson(resource1);

        mockMvc.perform(put("/resources/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .header("token", session.getToken()))
                .andExpect(status().isUnauthorized())
                .andDo(print());

        verify(sessionService, times(1))
                .sessionIsActual(session.getToken());
        verifyNoMoreInteractions(sessionService, resourceService);
    }

    @Test
    public void deleteResourceShouldBeSuccess() throws Exception {
        when(sessionService.sessionIsActual(session.getToken())).
                thenReturn(userRest);
        doNothing().when(resourceService).deleteResource(resource1.getId());

        mockMvc.perform(delete("/resources/{id}", resource1.getId())
                .header("token", session.getToken()))
                .andExpect(status().isOk());

        verify(sessionService, times(1))
                .sessionIsActual(session.getToken());
        verify(resourceService, times(1))
                .deleteResource(resource1.getId());
        verifyNoMoreInteractions(sessionService, resourceService);

    }
    @Test
    public void deleteResourceShouldThrowUnauthorizedException() throws Exception {
        when(sessionService.sessionIsActual(session.getToken())).
                thenThrow(UnauthorizedException.class);

        mockMvc.perform(delete("/resources/{id}", resource1.getId())
                .header("token", session.getToken()))
                .andExpect(status().isUnauthorized());

        verify(sessionService, times(1))
                .sessionIsActual(session.getToken());
        verifyNoMoreInteractions(sessionService, resourceService);
    }
}
