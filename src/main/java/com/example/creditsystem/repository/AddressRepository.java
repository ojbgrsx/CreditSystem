package com.example.creditsystem.repository;

import com.example.creditsystem.entity.Address;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    @NotNull Optional<Address> findById(Long id);
}
