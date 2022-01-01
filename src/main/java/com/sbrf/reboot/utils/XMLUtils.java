package com.sbrf.reboot.utils;

import com.sbrf.reboot.dto.Request;
import com.sbrf.reboot.dto.Response;
import lombok.NonNull;
import lombok.SneakyThrows;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class XMLUtils {

    public static String toXML(Response response) throws IOException,JAXBException{
        Marshaller mar= getMarshaller(Response.class);
        return toXml(response,mar);
    }

    public static String toXML(Request request) throws IOException,JAXBException {
        Marshaller mar= getMarshaller(Request.class);
        return toXml(request,mar);
    }

    public static Request XMLtoRequest (@NonNull String xml) throws JAXBException {
        return (Request) readXml(xml,Request.class);
    }

    public static Response XMLtoResponse (@NonNull String xml) throws JAXBException {
        return (Response) readXml(xml,Response.class);
    }

    @SneakyThrows
    private static Marshaller getMarshaller (Class c) {
        JAXBContext context = JAXBContext.newInstance(c);
        Marshaller mar= context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        return mar;
    }


    private static String toXml(Object o,Marshaller marshaller) throws IOException, JAXBException {
        String xml;
        try(StringWriter stringWriter = new StringWriter()) {
            marshaller.marshal(o, stringWriter);
            xml = stringWriter.getBuffer().toString();
        }
        catch (JAXBException | IOException e) {
            e.printStackTrace();
            throw e;
        }
        return xml;
    }


    private static Object readXml (String xml, Class c) throws JAXBException{
        Object obj;
        try(StringReader stringReader = new StringReader(xml)) {
            JAXBContext context = JAXBContext.newInstance(c);
            obj = context.createUnmarshaller()
                    .unmarshal(stringReader);
        } catch (JAXBException e) {
            e.printStackTrace();
            throw e;
        }
        return obj;
    }

}
