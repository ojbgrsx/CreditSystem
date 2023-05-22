package com.example.creditsystem.service;

import com.example.creditsystem.entity.CreditType;
import com.example.creditsystem.repository.CreditTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CreditTypeService {

    private final CreditTypeRepository creditTypeRepository;

    public CreditTypeService(CreditTypeRepository creditTypeRepository) {
        this.creditTypeRepository = creditTypeRepository;
    }

    @Transactional
    public List<CreditType> findAll(){
        return creditTypeRepository.findAll();
    }
}
