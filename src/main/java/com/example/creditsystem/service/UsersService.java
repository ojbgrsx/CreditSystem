package com.example.creditsystem.service;

import com.example.creditsystem.entity.Users;
import com.example.creditsystem.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    public Users findByUsername(String username){
        Optional<Users> user = usersRepository.findByUsername(username);
        return user.orElse(null);
    }

    @Transactional
    public Users findById(Long id){
        Optional<Users> user = usersRepository.findById(id);
        return user.orElse(null);
    }
}
