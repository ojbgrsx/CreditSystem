package com.example.creditsystem.repository;

import com.example.creditsystem.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormRepository extends JpaRepository<Form,Long>{

    Optional<Form> findByClientId(Long id);

}
