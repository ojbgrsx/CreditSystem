package com.example.creditsystem.service;

import com.example.creditsystem.entity.Users;
import com.example.creditsystem.entity.Worker;
import com.example.creditsystem.enums.Role;
import com.example.creditsystem.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkerService {

    private final WorkerRepository workerRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WorkerService(WorkerRepository workerRepository, PasswordEncoder passwordEncoder) {
        this.workerRepository = workerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public List<Worker> findAll(){
        return workerRepository.findAll();
    }

    @Transactional
    public void saveWorker(Worker worker, Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_WORKER);
        worker.setUsers(user);
        workerRepository.save(worker);
    }

    @Transactional
    public Worker findById(Long id){
        return workerRepository.findById(id).orElse(null);
    }

    @Transactional
    public Worker findByUsersId(Long id){
        return workerRepository.findByUsersId(id).orElse(null);
    }

    @Transactional
    public void updateWorker(Worker worker,Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        Authentication authentication = new UsernamePasswordAuthenticationToken(user,user.getPassword(),List.of(new SimpleGrantedAuthority(user.getRole().name())));
//        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//        securityContext.setAuthentication(authentication);
//        SecurityContextHolder.setContext(securityContext);
        worker.setUsers(user);
        workerRepository.saveAndFlush(worker);
    }


}
