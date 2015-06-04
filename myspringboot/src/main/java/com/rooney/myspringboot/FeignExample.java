package com.rooney.myspringboot;

import feign.*;

public interface FeignExample {

    @RequestLine("GET /")
    String home();

    @RequestLine("GET /result/{id}") 
    public MyResult result(@Param("id") Long id);   
    
}
