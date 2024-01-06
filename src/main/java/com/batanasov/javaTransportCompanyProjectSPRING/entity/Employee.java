package com.batanasov.javaTransportCompanyProjectSPRING.entity;

import com.batanasov.javaTransportCompanyProjectSPRING.Enums.Qualification;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.With;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Represents an employee in the transport company.
 * This includes information such as employee ID, name, position, salary, qualification, and associated company and contracts.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @NotEmpty(message = "Name cannot be empty or null")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Position cannot be empty or null")
    @Column(name = "position")
    private String position;

    @NotNull(message = "Salary cannot be empty or null")
    @PositiveOrZero(message = "Salary must be positive or zero")
    @Digits(integer = 10, fraction = 2, message = "Salary must be up to 2 digits after the decimal separator!")
    @Column(name = "salary")
    private BigDecimal salary;

    @NotNull(message = "Qualification cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "qualification")
    private Qualification qualification;

    @NotNull(message = "Company can be empty but not null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    private TransportCompany company;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TransportContract> transportContracts;
}
