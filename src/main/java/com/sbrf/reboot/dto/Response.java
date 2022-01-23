package com.sbrf.reboot.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Response")
@Data
public class Response {

    private String statusCode;

    //for xml un-marshal
    public Response() {}

    @JsonCreator
    public Response(@JsonProperty("statusCode")
                    String statusCode) {
        this.statusCode = statusCode;
    }

}
