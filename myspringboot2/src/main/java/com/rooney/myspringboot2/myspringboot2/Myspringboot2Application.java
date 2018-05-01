package com.rooney.myspringboot2.myspringboot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;

@SpringBootApplication
@RestController
public class Myspringboot2Application {

	@RequestMapping(value = "/stream")
	public void hellostreamer(HttpServletResponse response) throws Exception {

		response.setHeader("Transfer-Encoding", "chunked");
//		response.setHeader("Content-type", "text/html");
		response.setContentType("text/html; charset=utf-8");

//		ServletOutputStream outputStream = response.getOutputStream();
		OutputStreamWriter outputWriter = new OutputStreamWriter(response.getOutputStream());

		String line = "This is a line of text ";

		for (int j = 0; j < 100; j++) {

			for (int i = 0; i < 100; i++) {
				outputWriter.write(line + j + "-" + i);
			}

			outputWriter.flush();
			System.out.println("Flushing");
			Thread.sleep(1000);
		}

		outputWriter.close();
		System.out.println("Finished");
	}

	@RequestMapping(value = "/example", method = RequestMethod.GET)
	public String home(@RequestParam(value="query", required=false) String query) {
		System.out.println(query);
		return "Hello World! " + query;
//        return new MyResult("Hello World! - ");
	}

	public static void main(String[] args) {
		SpringApplication.run(Myspringboot2Application.class, args);
	}
}
