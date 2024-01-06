package com.batanasov.javaTransportCompanyProjectSPRING.DTO;

import lombok.Data;
@Data
public class EmployeeRevenueDTO {
    private Long employeeId;
    private String employeeName;
    private Double totalRevenue;

    public EmployeeRevenueDTO(Long employeeId, String employeeName, Double totalRevenue) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.totalRevenue = totalRevenue;
    }

    public EmployeeRevenueDTO() {
    }
}