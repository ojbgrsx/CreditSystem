package com.example.creditsystem.controller;

import com.example.creditsystem.entity.Address;
import com.example.creditsystem.entity.Client;
import com.example.creditsystem.entity.Users;
import com.example.creditsystem.repository.UsersRepository;
import com.example.creditsystem.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    private final UsersRepository usersRepository;

    @Autowired
    public ClientController(ClientService clientService, UsersRepository usersRepository) {
        this.clientService = clientService;
        this.usersRepository = usersRepository;
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
    public String cabinet() {
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
    public String showRegistrationForm(Model model){
        model.addAttribute("newClient",new Client());
        model.addAttribute("newUser",new Users());
        model.addAttribute("newAddress",new Address());
        return "client/register";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("client") Client client,
                               @ModelAttribute("user") Users users,
                               @ModelAttribute("address") Address address,
                               BindingResult bindingResult,
                               Model model) {
        Optional<Users> existing = usersRepository.findByUsername(users.getUsername());
//
        if (existing.isPresent() && existing.get().getUsername() != null && !existing.get().getUsername().isEmpty()) {
            bindingResult.rejectValue("username", null, "There is already an account registered with the same email");
        }
//
        if (bindingResult.hasErrors()) {
            model.addAttribute("client", new Client());
            return "client/register";
        }

        clientService.saveClient(client, users, address);
        return "redirect:/client?success";

    }

}
