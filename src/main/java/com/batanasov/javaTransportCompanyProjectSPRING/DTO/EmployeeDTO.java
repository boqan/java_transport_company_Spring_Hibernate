package com.batanasov.javaTransportCompanyProjectSPRING.DTO;

import com.batanasov.javaTransportCompanyProjectSPRING.Enums.Qualification;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class EmployeeDTO {
    private Long employeeId;
    private String name;
    private String position;
    private BigDecimal salary;
    private Qualification qualification;
    private Long companyId; // Changed from TransportCompany to Long
    private Set<Long> transportContractIds; // Changed from Set<TransportContract> to Set<Long>

//    public EmployeeDTO(Long employeeId, String name, String position, BigDecimal salary, Set<Qualification> qualifications, Long companyId, Set<Long> transportContractIds) {
//        this.employeeId = employeeId;
//        this.name = name;
//        this.position = position;
//        this.salary = salary;
//        this.qualifications = qualifications;
//        this.companyId = companyId;
//        this.transportContractIds = transportContractIds;
//    }

    public EmployeeDTO(Long employeeId, String name, String position, BigDecimal salary, Qualification qualification, Long companyId) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.qualification = qualification;
        this.companyId = companyId;
    }

    public EmployeeDTO() {
    }
}
