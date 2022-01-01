package com.sbrf.reboot.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "Request")
public class Request {

    private String atmNumber;

    public Request() {}

    @JsonCreator
    public Request(@JsonProperty("atmNumber")
                   String atmNumber) {
        this.atmNumber = atmNumber;
    }

}
