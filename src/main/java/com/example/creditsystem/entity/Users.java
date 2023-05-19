package com.example.creditsystem.entity;

import com.example.creditsystem.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Size(min = 8,max = 16,message = "Username length should be at 8-16 characters")
    @Column(name = "username",unique = true,nullable = false)
    String username;

//    @Size(min = 8,max = 16,message = "Password length should be at 8-16 characters")
    @Column(name = "password",nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    Role role;

    @OneToMany(mappedBy = "users",fetch = FetchType.LAZY)
    List<Client> clients;

    @OneToMany(mappedBy = "users",fetch = FetchType.LAZY)
    List<Worker> worker;
}
