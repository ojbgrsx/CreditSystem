package com.example.creditsystem.service;

import com.example.creditsystem.entity.Users;
import com.example.creditsystem.repository.UsersRepository;
import com.example.creditsystem.security.UsersDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> users = usersRepository.findByUsername(username);

        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new User(
                username,
                users.get().getPassword(),
                new UsersDetails(users.get()).getAuthorities()
        );
//        return new ClientDetails(client.get());
    }
}
