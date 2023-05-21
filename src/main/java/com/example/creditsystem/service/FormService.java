package com.example.creditsystem.service;

import com.example.creditsystem.entity.Client;
import com.example.creditsystem.entity.CreditType;
import com.example.creditsystem.entity.Form;
import com.example.creditsystem.enums.Status;
import com.example.creditsystem.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class FormService {

    private final FormRepository formRepository;

    @Autowired
    public FormService(FormRepository formRepository) {
        this.formRepository = formRepository;
    }

    @Transactional
    public Form findByClientId(Long id){

        Optional<Form> form = formRepository.findByClientId(id);

        return form.orElse(null);
    }

    @Transactional
    public void saveForm(Form form, Client client){
//        form.setBirthDate(Date.valueOf(form.getBirthDate()));
        form.setFormState(Status.PENDING);
        form.setInitialDate(LocalDate.now());
        form.setClient(client);
        formRepository.save(form);
    }

}
