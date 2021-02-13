package com.rooney.kong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class KongApplication {

	//curl "localhost:8080/helloworld?query=foo"
	@RequestMapping(value = "/helloworld", method = RequestMethod.GET)
	public String home(@RequestParam(value="query", required=false) String query) {
		System.out.println(query);
		return "Hello World! " + query;
	}

	public static void main(String[] args) {
		SpringApplication.run(KongApplication.class, args);
	}

}
