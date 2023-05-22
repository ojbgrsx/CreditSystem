package com.example.creditsystem.entity;

import com.example.creditsystem.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "credit")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JoinColumn(name = "client_id",nullable = false)
    @ManyToOne
    Client client;

    @JoinColumn(name = "credit_type_id")
    @ManyToOne
    CreditType creditType;

    @Column(name = "credit_state",nullable = false)
    @Enumerated(EnumType.STRING)
    Status creditState;

    @Column(name = "credit_amount",nullable = false)
    int creditAmount;

    @Column(name = "credit_taken_time",nullable = false,columnDefinition = "date default current_date")
    @Temporal(TemporalType.DATE)
    LocalDate creditTakenTime;

    @Column(name = "credit_payment_time")
    @Temporal(TemporalType.DATE)
    LocalDate creditPaymentTime;

    @Column(name = "monthly_payment",nullable = false)
    int monthlyPayment;

    @Column(name = "left_months")
    int leftMonths;

}
