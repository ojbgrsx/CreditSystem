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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final ClientService clientService;

    private final UsersRepository usersRepository;

    @Autowired
    public AuthController(ClientService clientService, UsersRepository usersRepository) {
        this.clientService = clientService;
        this.usersRepository = usersRepository;
    }

    @GetMapping()
    public String home(){
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("client",new Client());
        model.addAttribute("user",new Users());
        model.addAttribute("address",new Address());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("client") Client client,
                               @ModelAttribute("user") Users users,
                               @ModelAttribute("address") Address address,
                               BindingResult bindingResult,
                               Model model){

        Optional<Users> existing = usersRepository.findByUsername(users.getUsername());

        if(existing.isPresent() && existing.get().getUsername() != null && !existing.get().getUsername().isEmpty()){
            bindingResult.rejectValue("username",null,"There is already an account registered with the same email");
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("client",new Client());
            return "auth/register";
        }

        clientService.saveClient(client,users,address);
        return "redirect:/auth";

    }

}
