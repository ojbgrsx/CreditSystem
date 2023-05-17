package com.example.creditsystem.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;


@Entity
@Table(name = "form")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JoinColumn(name = "client_id", nullable = false)
    @OneToOne
    Client client;

    @Column(name = "initial_date", nullable = false,columnDefinition = "date default current_date")
    @Temporal(TemporalType.DATE)
    Date initialDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date",nullable = false)
    Date birthDate;

    @Column(name = "passport_number",length = 9,nullable = false)
    String passportNumber;

    @Column(name = "citizenship",nullable = false)
    String citizenship;

    @Column(name = "phone_number",length = 12)
    String phoneNumber;

    @Column(name = "family_status",length = 15)
    String familyStatus;

    @Column(name = "work_place",length = 25)
    String workPlace;

    @Column(name = "monthly_salary",nullable = false)
    int monthlySalary;

    @JoinColumn(name = "credit_type_id",nullable = false)
    @OneToOne
    CreditType creditType;

    @Column(name = "receive_cash",nullable = false)
    int receiveCash;

    @Column(name = "requested_period",nullable = false)
    @Temporal(TemporalType.DATE)
    Date requestedPeriod;

    @Column(name = "personal_property",columnDefinition = "varchar(100) default 'noting'")
    String personalProperty;

    @Column(name = "current_loans",nullable = false)
    int currentLoans;

    @Column(name = "form_state",length = 15,nullable = false)
    String formState;

    @JoinColumn(name = "worker_id",referencedColumnName = "id")
    @ManyToOne
    Worker worker;

}
