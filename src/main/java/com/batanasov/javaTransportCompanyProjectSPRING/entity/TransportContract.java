package com.batanasov.javaTransportCompanyProjectSPRING.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.With;

import java.time.LocalDate;

/**
 * Represents a transport contract.
 * Contains details about the contract such as contract ID, destination, start date, price, cargo details, status, weight, and associated company, client, vehicle, and employee.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "transport_contract")
public class TransportContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long contractId;

    @Column(name = "destination")
    private String destination;

    @FutureOrPresent(message = "Start date must be in the present or future")
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private LocalDate startDate;

    @PositiveOrZero(message = "Price must be positive or zero")
    @Column(name = "price")
    private Double price;


    @Column(name = "cargo_details")
    private String cargoDetails;

    @Column(name = "status")
    private Boolean status; // must be true for delivered or false for not delivered

    @Positive(message = "Weight must be a positive number")
    @Column(name = "weight")
    private Double weight;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "companyId")
    private TransportCompany company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clientId")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicleId")
    private Vehicle vehicle;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeId")
    private Employee employee;

}