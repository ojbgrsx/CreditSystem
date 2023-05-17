package com.example.creditsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    String creditState;

    @Column(name = "credit_amount",nullable = false)
    int creditAmount;

    @Column(name = "credit_taken_time",nullable = false,columnDefinition = "timestamp default current_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date creditTakenTime;

    @Column(name = "credit_payment_time")
    @Temporal(TemporalType.DATE)
    Date creditPaymentTime;

    @Column(name = "monthly_payment",nullable = false)
    int monthlyPayment;

    @Column(name = "left_months")
    int leftMonths;

}
