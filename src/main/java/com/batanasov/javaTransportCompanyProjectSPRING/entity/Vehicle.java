package com.batanasov.javaTransportCompanyProjectSPRING.entity;

import com.batanasov.javaTransportCompanyProjectSPRING.Enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.With;

import java.util.Objects;

/**
 * Represents a vehicle used by the transport company.
 * Includes information such as vehicle ID, type, license plate, capacity, and the associated company.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long vehicleId;

    @NotNull(message = "Type cannot be null")
    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @NotBlank(message = "License plate cannot be empty or null")
    @Column(name = "license_plate")
    private String licensePlate;

    @PositiveOrZero(message = "Capacity cannot be negative")
    @Column(name = "capacity")
    private Double capacity;

    @NotNull(message = "Company can be empty but not null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    private TransportCompany company;

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId);
    }

}