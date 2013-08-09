package org.mmiklas.resttutorial.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class HelloResponse {

    private String response;

    private Date serverTime;

    public HelloResponse(String response, Date serverTime) {
        this.response = response;
        this.serverTime = serverTime;
    }

    public String getResponse() {
        return response;
    }

    public Date getServerTime() {
        return serverTime;
    }

}
