package com.example.creditsystem.controller;

import com.example.creditsystem.entity.Users;
import com.example.creditsystem.entity.Worker;
import com.example.creditsystem.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ClientService clientService;

    private final UsersService usersService;

    private final FormService formService;


    private final WorkerService workerService;

    private final CreditService creditService;


    public AdminController(ClientService clientService, UsersService usersService, FormService formService, WorkerService workerService, CreditService creditService) {
        this.clientService = clientService;
        this.usersService = usersService;
        this.formService = formService;
        this.workerService = workerService;
        this.creditService = creditService;
    }

    @GetMapping()
    public String adminPage() {
        return "admin/index";
    }

    @GetMapping("/login")
    public String index() {
        return "main/login";
    }



    @GetMapping("/registerWorker")
    public String workerSignUpPage(Model model) {
        model.addAttribute("worker",new Worker());
        model.addAttribute("user", new Users());
        return "admin/workerRegister";
    }

    @PostMapping("/registerWorker")
    public String savingWorker(@Valid @ModelAttribute("user") Users user,
                               BindingResult usersBindingResult,
                               @Valid @ModelAttribute("worker") Worker worker,
                               BindingResult workerBindingResult,
                               Model model){
        Users existing = usersService.findByUsername(user.getUsername());

        if (existing != null) {
            usersBindingResult.rejectValue("username", null, "There is already an account registered with the same username");
        }
        if(usersBindingResult.hasErrors() || workerBindingResult.hasErrors()){
            model.addAttribute("user",user);
            model.addAttribute("worker",worker);
            return "admin/workerRegister";
        }

        workerService.saveWorker(worker,user);
        return "redirect:/admin?workerRegister";
    }


    @GetMapping("/registerAdmin")
    public String adminSignUpPage(Model model) {
        model.addAttribute("user",new Users());
        return "admin/adminRegister";
    }

    @PostMapping("/registerAdmin")
    public String savingAdmin(@Valid @ModelAttribute("user") Users user,
                              BindingResult usersBindingResult,
                              Model model
                              ){
        Users username = usersService.findByUsername(user.getUsername());

        if (username != null) {
            usersBindingResult.rejectValue("username", null, "There is already an account registered with the same username");
        }
        if(usersBindingResult.hasErrors()){
            model.addAttribute("user",user);
            return "admin/adminRegister";
        }

        usersService.saveAdmin(user);
        return "redirect:/admin?adminRegister";
    }

    @GetMapping("/forms")
    public String formsPage(Model model){
        model.addAttribute("formsList",formService.findAll());
        return "admin/forms";
    }

    @GetMapping("/workers")
    public String workersPage(Model model){
        model.addAttribute("workersList",workerService.findAll());
        return "admin/workers";
    }
    @GetMapping("/clients")
    public String clientsPage(Model model){
        model.addAttribute("clientsList",clientService.findAll());
        return "admin/clients";
    }
    @GetMapping("/credits")
    public String creditsPage(Model model){
        model.addAttribute("creditsList",creditService.findAll());
        return "admin/credits";
    }
}
