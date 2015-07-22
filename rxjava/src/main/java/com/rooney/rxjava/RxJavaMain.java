package com.rooney.rxjava;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by paul on 22/07/2015.
 */
@Configuration
@Controller
@EnableAutoConfiguration
public class RxJavaMain {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RxJavaMain.class, args);
    }
}
