package com.example.creditsystem.repository;

import com.example.creditsystem.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditRepository extends JpaRepository<Credit,Long>{

    List<Credit> findByClientId(Long id);

}