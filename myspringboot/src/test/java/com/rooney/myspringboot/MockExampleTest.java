package com.rooney.myspringboot;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import junit.framework.TestCase;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import com.jayway.restassured.response.Response;

import feign.Client;
import feign.Feign;
import feign.Request;
import feign.Request.Options;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@Ignore
@Configuration
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = ExampleImpl.class)
@ContextConfiguration(classes = MockExampleTest.class) //avoid Failed to load ApplicationContext
//@WebIntegrationTest(randomPort = true)
public class MockExampleTest extends TestCase {
	FeignExample feignExample;
	private MockMvc mockMvc;
	
	Client client;
	
	@Before 
	public void setUp() {
		StandaloneMockMvcBuilder standaloneSetup = MockMvcBuilders.standaloneSetup(new ExampleImpl());
		
		standaloneSetup.addFilters(new javax.servlet.Filter() {
            public void init(FilterConfig filterConfig) throws ServletException {
            }

            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                System.out.println("doFilter");
                if(request instanceof MockHttpServletRequest) {
                    MockHttpServletRequest mockRequest = (MockHttpServletRequest) request;
                    
                    if(mockRequest.getQueryString() != null) {
                        mockRequest.setQueryString(java.net.URLDecoder.decode(mockRequest.getQueryString(), "UTF-8"));
                    }
                    
                    Enumeration<String> parameterNames = request.getParameterNames();
                    while (parameterNames.hasMoreElements()) {
                        String name =  parameterNames.nextElement();
                        String[] values = mockRequest.getParameterValues(name);
                        for (int i = 0; i < values.length; i++) {
                            values[i] = java.net.URLDecoder.decode(values[i], "UTF-8");
                        }
                        
                        mockRequest.setParameter(name, values);
                    }
                    
                    
                }
                
            }

            public void destroy() {
            }
        });
        
		
        this.mockMvc = standaloneSetup.build();
		
		client = new MyFeignClient(mockMvc);
		
        feignExample = Feign.builder()
                .client(client)
                .decoder(new JacksonDecoder())
                .encoder(new JacksonEncoder())
                .target(FeignExample.class, "/example" ) ; //TODO / doesn't work, we need a base context
	}

	@Test
	public void testMockDecoding() throws Exception {
		//REQUEST PARAMS - check with request.getParameterValues("foo") when debugging
//		mockMvc.perform(get("/example/getWithQueryParams?foo={foo}&bar={bar}", "foo foo", "bar bar"));
//		mockMvc.perform(get("/getWithQueryParams?foo={foo}&bar={bar}", "[{\"operator\":\"lt\",\"value\":3,\"property\":\"id\"}]", "bar bar"));
		
		//PATH VARIABLES
//		mockMvc.perform(get("/result/foo foo"));
		mockMvc.perform(get("/result/{path}", "[{\"operator\":\"lt\",\"value\":3,\"property\":\"id\"}]"));
		
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
	

	@Test 
	public void testFeignGetWithQueryParams() {
	    //replicates issue of spaces being converted to '+' when param passed to controller method
	    
	    MyResult result = feignExample.getWithQueryParams("foo foo", "bar bar");
	    assertThat(result.result, is("Hello World! - getWithQueryParams: foo foobar bar"));
	}
	
	
    public static class MyFeignClient implements Client {
        private MockMvc mockMvc;

        public MyFeignClient(MockMvc mockMvc) {
            this.mockMvc = mockMvc;
        }

        public feign.Response execute(Request request, Options options) throws IOException {
            MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(request.url()); 
            
            builder.content(request.body());
            builder.characterEncoding(request.charset() != null ? request.charset().name() : Charset.defaultCharset().name());
            //TODO headers
            
            MockHttpServletResponse response;
            try {
                response = mockMvc.perform(builder).andReturn().getResponse();
            } catch (Exception e) {
                throw new IOException(e);
            }
            
            return feign.Response.create(response.getStatus(),
                    response.getErrorMessage() == null ? "" : response.getErrorMessage(),
                    Collections.<String, Collection<String>>emptyMap(), response.getContentAsByteArray());
        }
        
    }
    	
	
	
//	@Test 
//	public void testFeignResult() {
//	    MyResult result = feignExample.result(99L);
//	    assertThat(result.result, is("Hello World! - 99"));
//	}
//	
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
