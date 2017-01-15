package com.rooney.myspringboot;

import org.springframework.web.bind.annotation.*;

//@RequestMapping("/example") //TODO test with removed
public interface Example {

    @RequestMapping("/")
    String home();
    
    @RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
    MyResult result(Long id);
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ParentPojo create(@RequestBody ParentPojo parentPojo);

}