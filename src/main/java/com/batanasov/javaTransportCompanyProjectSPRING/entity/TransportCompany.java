package com.batanasov.javaTransportCompanyProjectSPRING.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.With;

import java.util.Objects;
import java.util.Set;

/**
 * Represents a transport company.
 * Includes details such as company ID, name, address, revenue, and associated employees, vehicles, and contracts.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "transport_company")
public class TransportCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @NotEmpty(message = "Name cannot be empty or null")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Address cannot be empty or null")
    @Column(name = "address")
    private String address;

    @PositiveOrZero(message = "Revenue must be positive or zero")
    @Column(name = "revenue")
    private Double revenue;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Employee> employees;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Vehicle> vehicles;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TransportContract> contracts;


    @Override
    public int hashCode() {
        return Objects.hash(companyId);
    }
}