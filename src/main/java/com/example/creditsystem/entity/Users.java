package com.example.creditsystem.entity;

import com.example.creditsystem.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

//    @Min(value = 8,message = "Username length should be at least 8 characters")
//    @Max(value = 16,message = "Username length should be at most 16 characters")
    @Column(name = "username",unique = true,nullable = false)
    String username;

//    @Min(value = 8,message = "Password length should be at least 8 characters")
//    @Max(value = 16,message = "Password length should be at most 16 characters")
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
