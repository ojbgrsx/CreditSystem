package com.example.creditsystem.controller;


import com.example.creditsystem.entity.Form;
import com.example.creditsystem.entity.Users;
import com.example.creditsystem.entity.Worker;
import com.example.creditsystem.enums.Status;
import com.example.creditsystem.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/worker")
public class WorkerController {
    private final ClientService clientService;

    private final UsersService usersService;

    private final FormService formService;

    private final AddressService addressService;

    private final CreditTypeService creditTypeService;

    private final WorkerService workerService;

    private final CreditService creditService;

    @Autowired
    public WorkerController(ClientService clientService, UsersService usersService, FormService formService, AddressService addressService, CreditTypeService creditTypeService, WorkerService workerService, CreditService creditService) {
        this.clientService = clientService;
        this.usersService = usersService;
        this.formService = formService;
        this.addressService = addressService;
        this.creditTypeService = creditTypeService;
        this.workerService = workerService;
        this.creditService = creditService;
    }

    @GetMapping("/cabinet")
    public String index(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = usersService.findByUsername(username);
        Worker worker = workerService.findByUsersId(users.getId());
        model.addAttribute("worker", worker);
        return "worker/index";
    }

    @GetMapping("/profile/{id}")
    public String profilePage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("worker", workerService.findById(id));
        return "worker/profile";
    }

    @PostMapping("/profile/{id}")
    public String workerSaving(@Valid @ModelAttribute Worker worker,
                               BindingResult workerBindingResult,
                               Model model, @PathVariable("id") Long id) {


        if (workerBindingResult.hasErrors()) {
            model.addAttribute("worker", worker);
            return "/worker/profile";
        }

        workerService.updateWorker(
                Worker.builder()
                        .id(id)
                        .firstName(worker.getFirstName())
                        .lastName(worker.getLastName())
                        .build(),
                usersService.findById(workerService.findById(id).getUsers().getId())
        );
        return "redirect:/worker/cabinet?success";
    }

    @GetMapping("/forms")
    public String formsPage(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = usersService.findByUsername(username);
        Worker worker = workerService.findByUsersId(users.getId());
        model.addAttribute("worker",worker);
        model.addAttribute("formsList",formService.findAll());
        return "worker/forms";
    }
    @GetMapping("/clients")
    public String clientsPage(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = usersService.findByUsername(username);
        Worker worker = workerService.findByUsersId(users.getId());
        model.addAttribute("worker",worker);
        model.addAttribute("clientsList",clientService.findAll());
        return "worker/clients";
    }
    @GetMapping("/credits")
    public String creditsPage(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = usersService.findByUsername(username);
        Worker worker = workerService.findByUsersId(users.getId());
        model.addAttribute("worker",worker);
        model.addAttribute("creditsList",creditService.findAll());
        return "worker/credits";
    }

    @GetMapping("/viewForm/{formId}")
    public String seeForm(@PathVariable("formId") Long id,Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = usersService.findByUsername(username);
        Worker worker = workerService.findByUsersId(users.getId());
        model.addAttribute("form",formService.findById(id));
        model.addAttribute("worker",worker);
        return "worker/viewForm";
    }

    @PostMapping("/formSaving/{bool}")
    public String saveForm(@PathVariable("bool") int bool,@ModelAttribute("form") Form form){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = usersService.findByUsername(username);
        Worker worker = workerService.findByUsersId(users.getId());
        form = formService.findById(form.getId());
        form.setWorker(worker);
        if (bool == 0){
            form.setFormState(Status.DECLINED);
            formService.saveForm(form,form.getClient());
            return "redirect:/worker/forms?declined";
        }else{
            form.setFormState(Status.ACCEPTED);
            formService.saveForm(form,form.getClient());
            creditService.saveCredit(form);
            return "redirect:/worker/forms?accepted";
        }
    }
}
