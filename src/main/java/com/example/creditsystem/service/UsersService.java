package com.example.creditsystem.service;

import com.example.creditsystem.entity.Users;
import com.example.creditsystem.enums.Role;
import com.example.creditsystem.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
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

    @Transactional
    public void saveAdmin(Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_ADMIN);
        usersRepository.save(user);

    }
}
