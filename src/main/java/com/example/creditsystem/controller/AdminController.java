package com.example.creditsystem.controller;

import com.example.creditsystem.service.ClientService;
import com.example.creditsystem.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ClientService clientService;
    private final WorkerService workerService;

    @Autowired
    public AdminController(ClientService clientService, WorkerService workerService) {
        this.clientService = clientService;
        this.workerService = workerService;
    }
    @GetMapping()
    public String adminPage(){
        return "admin/index";
    }

    @GetMapping("/login")
    public String index(){
        return "main/login";
    }


}
