package com.batanasov.javaTransportCompanyProjectSPRING.DTO;

import com.batanasov.javaTransportCompanyProjectSPRING.Enums.VehicleType;
import com.batanasov.javaTransportCompanyProjectSPRING.entity.TransportCompany;
import lombok.Data;

@Data
public class VehicleDTO {
    private Long vehicleId;
    private VehicleType type; // e.g., bus, truck
    private String licensePlate;
    private Integer capacity;
    private TransportCompany company;

    public VehicleDTO(Long vehicleId, VehicleType type, String licensePlate, Integer capacity, TransportCompany company) {
        this.vehicleId = vehicleId;
        this.type = type;
        this.licensePlate = licensePlate;
        this.capacity = capacity;
        this.company = company;
    }

    public VehicleDTO() {
    }
}
