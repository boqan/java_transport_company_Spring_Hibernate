package com.batanasov.javaTransportCompanyProjectSPRING.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TransportContractDTO {
    private Long contractId;
    private String destination;
    private LocalDate startDate;
    private Double price;
    private String cargoDetails;
    private Boolean status;
    private Double weight;

    // Replace complex objects with their ID fields
    private Long companyId;
    private Long clientId;
    private Long vehicleId;
    private Long employeeId;

    // Constructor with ID fields instead of objects
    public TransportContractDTO(Long contractId, String destination, LocalDate startDate, Double price, String cargoDetails, Boolean status, Double weight, Long companyId, Long clientId, Long vehicleId, Long employeeId) {
        this.contractId = contractId;
        this.destination = destination;
        this.startDate = startDate;
        this.price = price;
        this.cargoDetails = cargoDetails;
        this.status = status;
        this.weight = weight;
        this.companyId = companyId;
        this.clientId = clientId;
        this.vehicleId = vehicleId;
        this.employeeId = employeeId;
    }


    public TransportContractDTO() {
    }

}

