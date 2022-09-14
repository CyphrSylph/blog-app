package com.codeup.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

// @Controller defines that our class is a controller
@Controller
public class HelloController {

    // @GetMapping defines a method that should be invoked when a GET request is received for the specified URI
    @GetMapping("/hello")
    // @ResponseBody tells Spring that whatever is returned from this method should be the body of our response
    @ResponseBody
    public String hello() {
        return "Hello from Spring!";
    }

    @GetMapping("/hello/{name}")
    @ResponseBody
    // @PathVariable allows for dynamic additions to the path of a request
    public String sayHello(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/increment/{number}")
    @ResponseBody
    public String addOne(@PathVariable int number) {
        return number + " plus one is " + (number + 1) + "!";
    }
}


