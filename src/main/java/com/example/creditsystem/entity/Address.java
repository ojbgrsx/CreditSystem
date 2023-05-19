package com.example.creditsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "City shouldn't be empty")
    @Column(name = "city",nullable = false)
    String city;

    @NotEmpty(message = "Street shouldn't be empty")
    @Column(name = "street",nullable = false)
    String street;

    @NotEmpty(message = "House number shouldn't be empty")
    @Column(name = "house_number",nullable = false)
    String houseNumber;

    @Min(value = 1,message = "Floor's minimum value is 1")
    @Column(name = "floor")
    int floor;

    @Min(value = 1,message = "Apartment's minimum value is 1")
    @Column(name = "apartment")
    int apartment;

    @OneToOne(mappedBy = "address",fetch = FetchType.LAZY)
    Client client;
}
