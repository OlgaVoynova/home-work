package com.sbrf.reboot.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sbrf.reboot.utils.JSONUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class JSONUtilsTest {

    @SneakyThrows
    @Test
    void toJSONRequest() {
        Request request = new Request("ATM12345");
        System.out.println(JSONUtils.toJSON(request));
        Assertions.assertTrue(JSONUtils.toJSON(request).contains("atmNumber"));
    }

    @SneakyThrows
    @Test
    void toJSONResponse() {
        Response response = new Response("SUCCESS");
        System.out.println(JSONUtils.toJSON(response));
        Assertions.assertTrue(JSONUtils.toJSON(response).contains("statusCode"));
    }

    @Test
    void JSONtoRequest() throws JsonProcessingException, IOException {
        Request request = JSONUtils.JSONtoRequest("{\"atmNumber\" : \"ATM12345\"}");
        Assertions.assertEquals("ATM12345", request.getAtmNumber());
    }

    @Test
    void JSONtoResponse() throws JsonProcessingException,IOException {
        Response request = JSONUtils.JSONtoResponse("{\"statusCode\" : \"SUCCESS\"}");
        Assertions.assertEquals("SUCCESS", request.getStatusCode());
    }

}