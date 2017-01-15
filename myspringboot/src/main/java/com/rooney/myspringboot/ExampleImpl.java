<<<<<<< HEAD
package com.rooney.myspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class ExampleImpl { //implements Example {
    //TODO test with feign and test controller sharing Interface

    @RequestMapping(value = "/example", method = RequestMethod.GET)
    public String home(@RequestParam(value="query", required=false) String query) {
        System.out.println(query);
        return "Hello World!";
//        return new MyResult("Hello World! - ");
    }

    @RequestMapping(value = "/example/result/{id}", method = RequestMethod.GET)
    public MyResult result(@PathVariable String id, @RequestParam(value="query", required=false) String query) {
        System.out.println("**************** Hello World! - " + id + "**************** " + query);
        return new MyResult("**************** Hello World! - " + id + "****************");
    }

    @RequestMapping(value = "/example/getWithQueryParams", method = RequestMethod.GET)
    public MyResult getWithQueryParams(@RequestParam(required = false) String foo, @RequestParam(required = false) String bar) {
        System.out.println("****************foo: " + foo + " bar: " + bar + " ***********************");
        return new MyResult("Hello World! - getWithQueryParams: " + foo + bar);
    }
    
    @RequestMapping(value = "/example/getWithQueryParam", method = RequestMethod.GET)
    public MyResult getWithQueryParams(@RequestParam(required = false) String foo) {
        System.out.println("foo: " + foo);
        return new MyResult("Hello World! - getWithQueryParams: " + foo);
    }    
    
    
    @RequestMapping(value = "/example/create", method = RequestMethod.POST)
    public ParentPojo create(@RequestBody ParentPojo parentPojo) {
    	return parentPojo;
    }
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ExampleImpl.class, args);
    }
}
=======
package com.rooney.myspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rooney.myspringboot.validation.RestErrorHandler;



@RestController
@EnableAutoConfiguration
@Configuration
@Import({ RestErrorHandler.class }) //or scan
public class ExampleImpl { //implements Example {
    //TODO test with feign and test controller sharing Interface

    @RequestMapping(value = "/example", method = RequestMethod.GET)
    public String home(@RequestParam(value="query", required=false) String query) {
        System.out.println(query);
        return "Hello World!";
//        return new MyResult("Hello World! - ");
    }

    @RequestMapping(value = "/example/result/{id}", method = RequestMethod.GET)
    public MyResult result(@PathVariable String id, @RequestParam(value="query", required=false) String query) {
        System.out.println("**************** Hello World! - " + id + "**************** " + query);
        return new MyResult("**************** Hello World! - " + id + "****************");
    }

    @RequestMapping(value = "/example/getWithQueryParams", method = RequestMethod.GET)
    public MyResult getWithQueryParams(@RequestParam(required = false) String foo, @RequestParam(required = false) String bar) {
        System.out.println("****************foo: " + foo + " bar: " + bar + " ***********************");
        return new MyResult("Hello World! - getWithQueryParams: " + foo + bar);
    }
    
    @RequestMapping(value = "/example/getWithQueryParam", method = RequestMethod.GET)
    public MyResult getWithQueryParams(@RequestParam(required = false) String foo) {
        System.out.println("foo: " + foo);
        return new MyResult("Hello World! - getWithQueryParams: " + foo);
    }    
    
    //TODO
    @RequestMapping(value = "/example/getWithQueryParam", method = RequestMethod.PUT)
    public MyResult createWithVal(@Validated @RequestBody Foo foo) {
        System.out.println("foo: " + foo);
        return new MyResult("Hello World! - getWithQueryParams: " + foo);
    }   
    
    
	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource messageSource() {
	  ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
	  messageBundle.setBasename("classpath:messages");
	  messageBundle.setDefaultEncoding("UTF-8");
	  return messageBundle;
	}
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ExampleImpl.class, args);
    }
    
    
}
>>>>>>> origin/master
