package com.batanasov.javaTransportCompanyProjectSPRING.DTO;

import com.batanasov.javaTransportCompanyProjectSPRING.entity.Employee;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for representing the count of transport contracts associated with an employee.
 * This DTO is used to aggregate and transfer data related to the number of contracts handled by each employee.
 */
@Data
public class EmployeeContractCountDTO {
    private Employee employee;
    private Long contractCount;

    public EmployeeContractCountDTO(Employee employee, Long contractCount) {
        this.employee = employee;
        this.contractCount = contractCount;
    }

    public EmployeeContractCountDTO() {
    }
}

