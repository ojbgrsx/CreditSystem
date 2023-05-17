package com.example.creditsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "address")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "city",nullable = false)
    String city;

    @Column(name = "street",nullable = false)
    String street;

    @Column(name = "house_number",nullable = false)
    String houseNumber;

    @Column(name = "floor")
    int floor;

    @Column(name = "apartment")
    int apartment;

    @OneToOne(mappedBy = "address",fetch = FetchType.LAZY)
    Client client;
}
