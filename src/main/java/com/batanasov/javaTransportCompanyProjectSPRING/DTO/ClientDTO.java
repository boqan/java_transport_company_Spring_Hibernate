package com.batanasov.javaTransportCompanyProjectSPRING.DTO;

import com.batanasov.javaTransportCompanyProjectSPRING.entity.TransportContract;
import lombok.Data;

import java.util.Set;

@Data
public class ClientDTO {
    private Long clientId;
    private String name;
    private String contactDetails;
    private Boolean needsToPay;
    private Double outstandingPayments;
    private Set<TransportContract> services;

    public ClientDTO(Long clientId, String name, String contactDetails, Boolean needsToPay, Double outstandingPayments, Set<TransportContract> services) {
        this.clientId = clientId;
        this.name = name;
        this.contactDetails = contactDetails;
        this.needsToPay = needsToPay;
        this.outstandingPayments = outstandingPayments;
        this.services = services;
    }

    public ClientDTO() {
    }

}
