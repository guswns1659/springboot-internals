package com.springboot.springbootinternals;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello() {
        System.out.println("test");
        return "hello";
    }
}
