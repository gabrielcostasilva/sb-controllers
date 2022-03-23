package com.example.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MVCController {
    
    @GetMapping("/")
    public String init() {
        return "/index";
    }

    @PostMapping("/")
    public String init(@RequestParam String name, Model model) {
        
        if (Character.isLowerCase(name.charAt(0))) {
            throw new RuntimeException("A name must start with a capital letter");
        }

        model.addAttribute("username", name);

        return "/index";
    }

}
