package com.batanasov.javaTransportCompanyProjectSPRING.DTO;

import com.batanasov.javaTransportCompanyProjectSPRING.entity.Employee;
import lombok.Data;

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

