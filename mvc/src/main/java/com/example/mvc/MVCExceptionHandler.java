package com.example.mvc;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MVCExceptionHandler {
    
    @ExceptionHandler
    public String handleException(Exception e, Model model) {

        model.addAttribute("errorMessage", e.getMessage());
        
        return "/error";
    }
}
