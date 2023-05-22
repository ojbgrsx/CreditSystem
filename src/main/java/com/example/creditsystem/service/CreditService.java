package com.example.creditsystem.service;

import com.example.creditsystem.entity.Credit;
import com.example.creditsystem.entity.Form;
import com.example.creditsystem.enums.Status;
import com.example.creditsystem.repository.CreditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class CreditService {

    private final CreditRepository creditRepository;

    public CreditService(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    @Transactional
    public List<Credit> findAll(){
        return creditRepository.findAll();
    }

    @Transactional
    public void saveCredit(Form form){
        double percentage = form.getCreditType().getInterestRate();
        double time = form.getRequestedPeriod();
        double amount = form.getReceiveCash();
        double totalPercentage = percentage / 12 * time;
        double totalAmount = totalPercentage * amount / 100 + amount;
        double perMonthAmount = totalAmount / time;
        Credit credit = Credit.builder()
                .creditAmount(form.getReceiveCash())
                .creditState(Status.IN_PROGRESS)
                .client(form.getClient())
                .creditType(form.getCreditType())
                .leftMonths(form.getRequestedPeriod())
                .creditTakenTime(LocalDate.now())
                .creditPaymentTime(LocalDate.now().plusMonths(form.getRequestedPeriod()))
                .monthlyPayment((int) Math.ceil(perMonthAmount))
                .build();
        creditRepository.save(credit);
    }
}
