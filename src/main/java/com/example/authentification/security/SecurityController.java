package com.example.authentification.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SecurityController {

    @GetMapping("")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping("protected")
    public String protect(){
        return "Protected";
    }

}
