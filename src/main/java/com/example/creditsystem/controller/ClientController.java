package com.example.creditsystem.controller;

import com.example.creditsystem.entity.*;
import com.example.creditsystem.repository.CreditTypeRepository;
import com.example.creditsystem.service.AddressService;
import com.example.creditsystem.service.ClientService;
import com.example.creditsystem.service.FormService;
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

    private final FormService formService;

    private final AddressService addressService;

    private final CreditTypeRepository creditTypeRepository;

    @Autowired
    public ClientController(ClientService clientService, UsersService usersService1, FormService formService, AddressService addressService, CreditTypeRepository creditTypeRepository) {
        this.clientService = clientService;
        this.usersService = usersService1;
        this.formService = formService;
        this.addressService = addressService;
        this.creditTypeRepository = creditTypeRepository;
    }


    @GetMapping("/cabinet")
    public String cabinet(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users users = usersService.findByUsername(username);
        Client client = clientService.findByUsersId(users.getId());
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

        System.out.println();

        if (existing != null) {
            usersBindingResult.rejectValue("username", null, "There is already an account registered with the same username");
        }

        if (usersBindingResult.hasErrors() || addressBindingResult.hasErrors() || clientBindingResult.hasErrors()) {
            model.addAttribute("client", client);
            model.addAttribute("users", users);
            model.addAttribute("address", address);
            return "client/register";
        }

        clientService.saveClient(client, users, address);
        return "redirect:/main?success";

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
                .users(usersService.findById(clientService.findById(id).getUsers().getId()))
                .build());
        return "redirect:/client/cabinet";

    }

    @GetMapping("/formsMenu/{id}")
    public String formsMenu(Model model,@PathVariable("id") Long id){
        model.addAttribute("client",clientService.findById(id));
        return "client/formsMenu";
    }

    @GetMapping("/formsList/{id}")
    public String formsList(Model model,@PathVariable("id") Long id){
        Client client = clientService.findById(id);
        if (!client.getFormList().isEmpty()){
            model.addAttribute("client",client);
            model.addAttribute("formsList",client.getFormList());
            return "client/formsList";
        }
        return String.format("redirect:/client/formsMenu/%d?notExist",id);
    }

    @GetMapping("/form/{id}")
    public String formPage(@PathVariable("id") Long id,
                           Model model){
        model.addAttribute("form",new Form());
        model.addAttribute("client",clientService.findById(id));
        model.addAttribute("creditType",creditTypeRepository.findAll());
        return "client/form";
    }

    @PostMapping("/form/{id}")
    public String savingForm(@PathVariable("id") Long id,
                             @ModelAttribute("form") Form form){
        formService.saveForm(form,clientService.findById(id));
        return "redirect:/client/formsMenu/"+id;
    }


}
