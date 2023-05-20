package com.example.creditsystem.controller;


import com.example.creditsystem.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WorkerController {
    private final WorkerService workerService;

    @Autowired
    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping("/worker")
    public String index(Model model) {
        model.addAttribute("workers", workerService.findAll());
        return "worker/index";
    }
}
