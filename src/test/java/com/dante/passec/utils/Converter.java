package com.dante.passec.utils;

import com.dante.passec.model.ResourceData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Convert POJO objects to json objects
 */
public class Converter {

    public static String toJson(Object o) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return  objectMapper.writeValueAsString(o);
    }

    public static Object toObject(String json) throws IOException {
        ObjectMapper objectMappe = new ObjectMapper();
        return objectMappe.readValue(json, ResourceData.class);
    }
}
