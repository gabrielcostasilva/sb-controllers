package com.example.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RESTController {
    
    @GetMapping("/")
    public ResponseEntity<String> init() {
        return ResponseEntity.ok("Hello world?");
    }

    @PostMapping("/{name}")
    public ResponseEntity<String> init(@PathVariable String name) {
        
        if (Character.isLowerCase(name.charAt(0))) {
            throw new RuntimeException("A name must start with a capital letter");
        }

        return ResponseEntity.ok("Hello World, " + name);
    }

}
