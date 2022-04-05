package com.example.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RESTExceptionHandler {
    
    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception e) {
        
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
