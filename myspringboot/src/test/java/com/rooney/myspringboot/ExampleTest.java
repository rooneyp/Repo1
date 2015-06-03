package com.rooney.myspringboot;

import com.jayway.restassured.RestAssured;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static         org.hamcrest.Matchers.*;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Example.class)
@WebIntegrationTest(randomPort = true)
public class ExampleTest extends TestCase {
	@Value("${local.server.port}")
	int port;

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@org.junit.Test
	public void testApp() {

        when().
                get("/").
        then().
                statusCode(HttpStatus.SC_OK).
                content(Matchers.is("Hello World!"));
                //body("result", Matchers.is("Hello World!"));
    }
}
