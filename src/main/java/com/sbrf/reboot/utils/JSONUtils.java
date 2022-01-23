package com.sbrf.reboot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbrf.reboot.dto.Request;
import com.sbrf.reboot.dto.Response;

import java.io.IOException;

public class JSONUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String toJSON (Request request) throws JsonProcessingException{
        String jsonString;
        try {
            jsonString = MAPPER.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw e;
        }
        return jsonString;
    }

    public static String toJSON (Response response) throws JsonProcessingException{
        String jsonString;
        try {
            jsonString = MAPPER.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw e;
        }
        return jsonString;
    }

    public static Request JSONtoRequest (String json) throws IOException {
        Request request;
        try {
            request = MAPPER.readerFor(Request.class).readValue(json,Request.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return request;
    }

    public static Response JSONtoResponse (String json) throws IOException {
        Response response;
        try {
            response = MAPPER.readerFor(Response.class).readValue(json, Response.class);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw ioe;
        }
        return response;
    }
}
