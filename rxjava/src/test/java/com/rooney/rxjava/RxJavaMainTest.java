package com.rooney.rxjava;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RxJavaMain.class)
@WebIntegrationTest(randomPort = true)
public class RxJavaMainTest {

    @Value("${local.server.port}")
    int port;


    @Before public void setUp() {
        RestAssured.port = port;
    }

    @Test public void testHome() {
        Response response =
                when().
                        get("/").
                        then().
                        statusCode(HttpStatus.SC_OK).
                        content(Matchers.is("Hello World!")).
                        extract().
                        response();
        //body("result", Matchers.is("Hello World!"));

        System.out.println(response.asString());
    }


} 
