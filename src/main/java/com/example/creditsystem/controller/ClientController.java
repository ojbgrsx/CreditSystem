package com.example.creditsystem.controller;

import com.example.creditsystem.entity.Address;
import com.example.creditsystem.entity.Client;
import com.example.creditsystem.entity.Users;
import com.example.creditsystem.service.ClientService;
import com.example.creditsystem.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    private final UsersService usersService;

    @Autowired
    public ClientController(ClientService clientService, UsersService usersService1) {
        this.clientService = clientService;
        this.usersService = usersService1;
    }

    @GetMapping
    public String hello() {
        return "client/index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "client/login";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "client/about";
    }

    @GetMapping("/terms")
    public String termsPage() {
        return "client/terms";
    }

    @GetMapping("/contacts")
    public String contactsPage() {
        return "client/contacts";
    }

    @GetMapping("/private/cabinet")
    public String cabinet(Model model) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = usersService.findByUsername(username);
        Client client = clientService.findById(users.getId());
        model.addAttribute("clientData", Client.builder()
                .id(client.getId())
                .cash(client.getCash())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .build());
        model.addAttribute("clientAddress",client.getAddress());
        return "client/private/cabinet";
    }


//    @GetMapping("/{id}")
//    public String client(@PathVariable("id") Long id, Model model) throws Exception {
//        model.addAttribute("client", clientService.findById(id));
//        return "client/client_page";
//    }

//    @DeleteMapping("/{id}")
//    public String deleteClient(@PathVariable("id") Long id) throws Exception {
//        clientService.deleteClient(clientService.findById(id));
//        return "redirect:/auth";
//    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("users", new Users());
        model.addAttribute("address", new Address());
        return "client/register";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("client") Client client,
                               BindingResult clientBindingResult,
                               @Valid @ModelAttribute("users") Users users,
                               BindingResult usersBindingResult,
                               @Valid @ModelAttribute("address") Address address,
                               BindingResult addressBindingResult,
                               Model model) {

        Users existing = usersService.findByUsername(users.getUsername());

        if (existing.getUsername() != null && !existing.getUsername().isEmpty()) {
            usersBindingResult.rejectValue("username", null, "There is already an account registered with the same email");
        }

        if (usersBindingResult.hasErrors() || addressBindingResult.hasErrors() || clientBindingResult.hasErrors()) {
            model.addAttribute("client", client);
            model.addAttribute("users", users);
            model.addAttribute("address", address);
            return "client/register";
        }

        clientService.saveClient(client, users, address);
        return "redirect:/client?success";

    }

}
