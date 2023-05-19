package com.example.creditsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Entity
@Table(name = "client")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id",nullable = false,unique = true)
    Users users;

    @NotEmpty(message = "Name shouldn't be empty")
    @Column(name = "first_name")
    String firstName;

    @NotEmpty(message = "Surname shouldn't be empty")
    @Column(name = "last_name")
    String lastName;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    Address address;

    @Column(name = "cash")
    int cash;


}
