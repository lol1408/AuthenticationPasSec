package com.dante.passec.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ResponseBodyTestConvert {

    @Test
    public void testMapper() throws JsonProcessingException {
        ResponseBody<Session> responseBody = new ResponseBody<>();
        responseBody.setResponse("Hello", "200");
        ObjectMapper mapper = new ObjectMapper();
        String string = mapper.writeValueAsString(responseBody);
        System.out.println(string);
    }

}
