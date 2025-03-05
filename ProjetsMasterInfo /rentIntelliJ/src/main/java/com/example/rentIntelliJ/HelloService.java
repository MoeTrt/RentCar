package com.example.rentIntelliJ;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloService {
    @GetMapping("/")
    public String hello() {
        return "Hello je suis le test d'affichage";
    }
}