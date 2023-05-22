package com.example.creditsystem.controller;

import com.example.creditsystem.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

    @GetMapping("/back")
    public String backPage(Authentication authentication){
        List<? extends GrantedAuthority> list = authentication.getAuthorities().stream().toList();
        if (list.get(0).equals(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name()))) {
            return "redirect:/admin";
        } else if (list.get(0).equals(new SimpleGrantedAuthority(Role.ROLE_CLIENT.name()))) {
            return "redirect:/client/cabinet";
        } else if (list.get(0).equals(new SimpleGrantedAuthority(Role.ROLE_WORKER.name()))) {
            return "redirect:/worker/cabinet";
        } else {
            return "redirect:/main/index";
        }
    }
}
