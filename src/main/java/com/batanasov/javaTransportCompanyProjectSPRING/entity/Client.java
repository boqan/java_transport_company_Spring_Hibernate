package com.batanasov.javaTransportCompanyProjectSPRING.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.With;

import java.util.Set;

/**
 * Represents a client in the transport system.
 * Each client has a unique ID, name, contact details, and information about payments and contracts.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @NotEmpty(message = "Name cannot be empty")
    @Pattern(regexp = "^([A-Z]).*", message = "Client name has to start with capital letter")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Contact details cannot be empty")
    @Pattern(regexp = "^\\+\\d+$", message = "Invalid phone number format")
    @Column(name = "contact_details")
    private String contactDetails;

    @NotNull(message = "Outstanding payments can be empty but not null")
    @PositiveOrZero(message = "Outstanding payments cannot be negative")
    @Column(name = "outstanding_payments")
    private Double outstandingPayments;

    @Column(name = "needs_to_pay")
    private Boolean needsToPay = false; // Default to false

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TransportContract> contracts;

}
