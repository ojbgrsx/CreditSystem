package com.example.creditsystem.service;

import com.example.creditsystem.entity.Address;
import com.example.creditsystem.entity.Client;
import com.example.creditsystem.entity.Users;
import com.example.creditsystem.enums.Role;
import com.example.creditsystem.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void saveClient(Client client, Users users, Address address) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setRole(Role.ROLE_CLIENT);
        client.setAddress(address);
        client.setUsers(users);
        client.setCash(0);
        clientRepository.save(client);
    }

    @Transactional
    public void updateClient(Client client){
        clientRepository.saveAndFlush(client);
    }

    @Transactional
    public void deleteClient(Client client){
        clientRepository.delete(client);
    }

    @Transactional
    public Client findById(Long id){
        Optional<Client> client = clientRepository.findById(id);
        return client.orElseGet(Client::new);
    }


    @Transactional
    public Client findByUsersId(Long id){
        Optional<Client> client = clientRepository.findByUsersId(id);
        return client.orElseGet(Client::new);
    }

    @Transactional
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

}
