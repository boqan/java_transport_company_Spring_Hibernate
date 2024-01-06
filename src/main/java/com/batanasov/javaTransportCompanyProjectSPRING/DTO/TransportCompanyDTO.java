package com.batanasov.javaTransportCompanyProjectSPRING.DTO;

import com.batanasov.javaTransportCompanyProjectSPRING.entity.Employee;
import com.batanasov.javaTransportCompanyProjectSPRING.entity.TransportContract;
import com.batanasov.javaTransportCompanyProjectSPRING.entity.Vehicle;
import lombok.Data;

import java.util.Set;

@Data
public class TransportCompanyDTO {
    private Long companyId;
    private String name;
    private String address;
    private Double revenue;
    private Set<Employee> employees;
    private Set<Vehicle> vehicles;
    private Set<TransportContract> contracts;

    public TransportCompanyDTO(Long companyId, String name, String address, Double revenue, Set<Employee> employees, Set<Vehicle> vehicles, Set<TransportContract> contracts) {
        this.companyId = companyId;
        this.name = name;
        this.address = address;
        this.revenue = revenue;
        this.employees = employees;
        this.vehicles = vehicles;
        this.contracts = contracts;
    }

    public TransportCompanyDTO() {
    }

    public TransportCompanyDTO(Long companyId, String name, String address, Double revenue) {
        this.companyId = companyId;
        this.name = name;
        this.address = address;
        this.revenue = revenue;
    }
}
