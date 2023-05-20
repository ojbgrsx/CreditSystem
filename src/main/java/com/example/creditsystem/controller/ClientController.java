package com.example.creditsystem.controller;

import com.example.creditsystem.entity.Address;
import com.example.creditsystem.entity.Client;
import com.example.creditsystem.entity.Users;
import com.example.creditsystem.service.ClientService;
import com.example.creditsystem.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/cabinet")
    public String cabinet(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = usersService.findByUsername(username);
        Client client = clientService.findById(users.getId());
        model.addAttribute("clientAddress", client.getAddress());
        client.setAddress(null);
        model.addAttribute("clientData", client);
        return "client/cabinet";
    }


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
        return "redirect:/all?success";

    }

    @GetMapping("/profile/{id}")
    public String profilePage(Model model, @PathVariable("id") Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = usersService.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("client", clientService.findById(id));
        model.addAttribute("address", clientService.findById(id).getAddress());
        return "client/changeProfile";
    }

    @PatchMapping("/profile/{id}")
    public String profileChange(@PathVariable("id") Long id, @Valid @ModelAttribute("client") Client client, BindingResult clientResult,
                                @Valid @ModelAttribute("address") Address address, BindingResult addressResult,
                                Model model) {
        if (clientResult.hasErrors() || addressResult.hasErrors()) {
            model.addAttribute("client", client);
            model.addAttribute("address", address);
            return "redirect:/client/profile";
        }
        clientService.updateClient(Client.builder()
                .id(id)
                .cash(client.getCash())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .address(address)
                .users(usersService.findById(id))
                .build());
        return "redirect:/client/cabinet";

    }

    @GetMapping("/formsMenu")
    public String formsMenu(){
        return "client/formsMenu";
    }


}
