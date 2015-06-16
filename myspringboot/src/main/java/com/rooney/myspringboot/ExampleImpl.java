package com.rooney.myspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class ExampleImpl { //implements Example {
    //TODO test with feign and test controller sharing Interface

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(@RequestParam(value="query", required=false) String query) {
        System.out.println(query);
        return "Hello World!";
//        return new MyResult("Hello World! - ");
    }

    @RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
    public MyResult result(@PathVariable Long id, @RequestParam(value="query", required=false) String query) {
        System.out.println(query);
        return new MyResult("Hello World! - " + id);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ExampleImpl.class, args);
    }
}
