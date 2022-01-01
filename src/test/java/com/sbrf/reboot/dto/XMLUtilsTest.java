package com.sbrf.reboot.dto;


import com.sbrf.reboot.utils.XMLUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;

class XMLUtilsTest {

    @SneakyThrows
    @Test
    void toXMLRequest() {
        Request request = new Request("ATM12345");
        Assertions.assertTrue(XMLUtils.toXML(request).contains("atmNumber"));
    }

    @SneakyThrows
    @Test
    void toXMLResponse() {
        Response response = new Response("SUCCESS");
        Assertions.assertTrue(XMLUtils.toXML(response).contains("statusCode"));
    }

    @Test
    void XMLtoRequest() throws JAXBException {
        Request request = XMLUtils.XMLtoRequest("<Request><atmNumber>ATM12345</atmNumber></Request>");
        Assertions.assertEquals("ATM12345", request.getAtmNumber());
    }

    @Test
    void XMLtoResponse() throws JAXBException {
        Response request = XMLUtils.XMLtoResponse("<Response><statusCode>SUCCESS</statusCode></Response>");
        Assertions.assertEquals("SUCCESS", request.getStatusCode());
    }

}