package com.example.creditsystem.entity;


import com.example.creditsystem.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
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
    @ManyToOne(cascade = CascadeType.ALL)
    Client client;

    @Column(name = "initial_date", columnDefinition = "date default current_date")
    @Temporal(TemporalType.DATE)
    LocalDate initialDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date", nullable = false, columnDefinition = "DATE")
    LocalDate birthDate;

    @Column(name = "passport_number", length = 9, nullable = false)
    String passportNumber;

    @Column(name = "citizenship", nullable = false)
    String citizenship;

    @Column(name = "phone_number", length = 16)
    String phoneNumber;

    @Column(name = "family_status", length = 125)
    String familyStatus;

    @Column(name = "work_place", length = 25)
    String workPlace;

    @Column(name = "monthly_salary", nullable = false)
    int monthlySalary;

    @JoinColumn(name = "credit_type_id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    CreditType creditType;

    @Column(name = "receive_cash", nullable = false)
    int receiveCash;

    @Column(name = "requested_period", nullable = false)
    int requestedPeriod;

    @Column(name = "personal_property", columnDefinition = "varchar(100) default 'noting'")
    String personalProperty;

    @Column(name = "current_loans", nullable = false)
    int currentLoans;

    @Column(name = "form_state", nullable = false)
    @Enumerated(EnumType.STRING)
    Status formState;

    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    @ManyToOne
    Worker worker;

}
