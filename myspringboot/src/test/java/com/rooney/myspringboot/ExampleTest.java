package com.rooney.myspringboot;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import junit.framework.*;

import org.apache.http.*;
import org.hamcrest.*;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.*;
import org.springframework.test.context.junit4.*;

import com.jayway.restassured.*;
import com.jayway.restassured.response.Response;

import feign.*;
import feign.jackson.*;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ExampleImpl.class)
@WebIntegrationTest(randomPort = true)
public class ExampleTest extends TestCase {
	@Value("${local.server.port}")
	int port;

	FeignExample feignExample;
	
	@Before public void setUp() {
		RestAssured.port = port;
		
		feignExample = Feign.builder()
                .decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder())
                .target(FeignExample.class, "http://localhost:" + port + "/example") ; 
	}


	@Test 
	public void testFeignResult() {
	    MyResult result = feignExample.result(99L);
	    assertThat(result.result, is("**************** Hello World! - 99****************"));
	}
	
	@Test 
	public void testFeignGetWithQueryParams() {
	    MyResult result = feignExample.getWithQueryParams("foo foo", "bar bar");
	    assertThat(result.result, is("Hello World! - getWithQueryParams: foo foobar bar"));
	}
	
    @Test 
    public void testFeignGetWithQueryParam() {
        MyResult result = feignExample.getWithQueryParam("foo");
        assertThat(result.result, is("Hello World! - getWithQueryParams: foo"));
    }	
	
	@Test 
	public void testRESTHome() {
	    Response response = 
        when().
                get("/example").
        then().
                statusCode(HttpStatus.SC_OK).
                content(Matchers.is("Hello World!")).
        extract().
            response();
                //body("result", Matchers.is("Hello World!"));
	    
	   System.out.println(response.asString());
    }
	
	
    @Test 
    public void testRESTResult() {
        when().
                get("/example/result/{id}", "99").
        then().
                statusCode(HttpStatus.SC_OK).
                body("result", Matchers.is("**************** Hello World! - 99****************"));
        
    }	
}
