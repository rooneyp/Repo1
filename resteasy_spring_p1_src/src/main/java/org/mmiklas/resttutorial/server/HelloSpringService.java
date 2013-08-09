package org.mmiklas.resttutorial.server;

import java.util.Date;

import javax.inject.Named;

import org.mmiklas.resttutorial.model.Gender;
import org.mmiklas.resttutorial.model.HelloMessage;
import org.mmiklas.resttutorial.model.HelloResponse;

@Named
public class HelloSpringService {

    public String sayTextHello(String msg) {
        return msg + "--> Hello";
    }

    public HelloResponse sayJavaBeanHello(HelloMessage msg) {
        return new HelloResponse(msg.getMsg() + "--> Hello " + 
    (msg.getGender() == Gender.MALE ? "Sir" : "Madam"), new Date());
    }
}