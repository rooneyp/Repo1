package com.rooney.myspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class ExampleImpl implements Example {

    public String home() {
        // return new Result("Hello World!");
        return "Hello World!";
    }

    public MyResult result(@PathVariable Long id) {
        return new MyResult("Hello World! - " + id);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ExampleImpl.class, args);
    }
}
