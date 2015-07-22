package com.rooney.myspringboot;

import javax.inject.Named;

import feign.Param;
import feign.RequestLine;

public interface FeignExample {

    @RequestLine("GET /")
    String home();

    @RequestLine("GET /result/{id}") 
    public MyResult result(@Param("id") Long id);   

    //FAIL: too many body params when no parm annotations and named query params in url
    @RequestLine("GET /getWithQueryParams?foo={foo}&bar={bar}") 
    public MyResult getWithQueryParams(@Named("foo") String foo, @Named("bar") String bar);
    
    //FAIL: looks like POST is chosen
//    @RequestLine("GET /getWithQueryParam") 
    @RequestLine("GET /getWithQueryParam?foo={foo}") //SUCCESS!!! use queries 
    public MyResult getWithQueryParam(@Named("foo") String foo); //FAILS with a) no annotation, b) @Named("foo") , c) @Named("foo")
    
    //TODO @Body
    
    //TODO feign using url named params, controller use Map or query params
}
