package com.rooney.myspringboot;

import static com.jayway.restassured.RestAssured.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import junit.framework.TestCase;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jayway.restassured.response.Response;

@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = ExampleImpl.class)
@ContextConfiguration(classes = MockExampleTest.class) //avoid Failed to load ApplicationContext
//@WebIntegrationTest(randomPort = true)
public class MockExampleTest extends TestCase {
//	FeignExample feignExample;
	private MockMvc mockMvc;
	
	@Before 
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new ExampleImpl()).build();
		
//		feignExample = Feign.builder()
//                .decoder(new JacksonDecoder())
//                .encoder(new JacksonEncoder())
//                .target(FeignExample.class, "http://localhost:" + port) ; // + "/example");
	}

	@Test
	public void testMockDecoding() throws Exception {
		//REQUEST PARAMS
		mockMvc.perform(get("/getWithQueryParams?foo={foo}&bar={bar}", "foo foo", "bar bar"));
		mockMvc.perform(get("/getWithQueryParams?foo={foo}&bar={bar}", "[{\"operator\":\"lt\",\"value\":3,\"property\":\"id\"}]", "bar bar"));
		
		//PATH VARIABLES
		mockMvc.perform(get("/result/foo foo"));
		mockMvc.perform(get("/result/[{\"operator\":\"lt\",\"value\":3,\"property\":\"id\"}]"));
		//FAIL: java.lang.IllegalArgumentException: Not enough variable values available to expand '"operator"'
//		at org.springframework.web.util.UriComponents$VarArgsTemplateVariables.getValue(UriComponents.java:297)
//		at org.springframework.web.util.UriComponents.expandUriComponent(UriComponents.java:221)
//		at org.springframework.web.util.HierarchicalUriComponents$FullPathComponent.expand(HierarchicalUriComponents.java:650)
//		at org.springframework.web.util.HierarchicalUriComponents.expandInternal(HierarchicalUriComponents.java:319)
//		at org.springframework.web.util.HierarchicalUriComponents.expandInternal(HierarchicalUriComponents.java:46)
//		at org.springframework.web.util.UriComponents.expand(UriComponents.java:163)
//		at org.springframework.web.util.UriComponentsBuilder.buildAndExpand(UriComponentsBuilder.java:313)
//		at org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.<init>(MockHttpServletRequestBuilder.java:125)
//		at org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(MockMvcRequestBuilders.java:46)
//		at com.rooney.myspringboot.MockExampleTest.testMockDecoding(MockExampleTest.java:45)



	}
	
	
//	@Test 
//	public void testFeignResult() {
//	    MyResult result = feignExample.result(99L);
//	    assertThat(result.result, is("Hello World! - 99"));
//	}
//	
//	@Test 
//	public void testFeignGetWithQueryParams() {
//	    MyResult result = feignExample.getWithQueryParams("foo", "bar");
//	    assertThat(result.result, is("Hello World! - getWithQueryParams: foobar"));
//	}
//	
//    @Test 
//    public void testFeignGetWithQueryParam() {
//        MyResult result = feignExample.getWithQueryParam("foo");
//        assertThat(result.result, is("Hello World! - getWithQueryParams: foo"));
//    }	
	
	@Test 
	public void testRESTHome() {
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
	
	
    @Test 
    public void testRESTResult() {
        when().
                get("/result/{id}", "99").
        then().
                statusCode(HttpStatus.SC_OK).
                body("result", Matchers.is("Hello World! - 99"));
        
    }	
}
