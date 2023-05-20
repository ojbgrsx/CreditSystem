package com.example.creditsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {
    @GetMapping()
    public String hello() {
        return "main/index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "main/login";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "main/about";
    }

    @GetMapping("/terms")
    public String termsPage() {
        return "main/terms";
    }

    @GetMapping("/contacts")
    public String contactsPage() {
        return "main/contacts";
    }

}
