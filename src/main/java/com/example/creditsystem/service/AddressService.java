package com.example.creditsystem.service;

import com.example.creditsystem.entity.Address;
import com.example.creditsystem.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Transactional
    public Address findById(Long id){
        Optional<Address> address = addressRepository.findById(id);
        return address.orElse(null);
    }
}
