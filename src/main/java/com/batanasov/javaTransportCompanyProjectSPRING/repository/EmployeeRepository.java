package com.batanasov.javaTransportCompanyProjectSPRING.repository;

import com.batanasov.javaTransportCompanyProjectSPRING.DTO.EmployeeContractCountDTO;
import com.batanasov.javaTransportCompanyProjectSPRING.DTO.EmployeeDTO;
import com.batanasov.javaTransportCompanyProjectSPRING.DTO.EmployeeRevenueDTO;
import com.batanasov.javaTransportCompanyProjectSPRING.Enums.Qualification;
import com.batanasov.javaTransportCompanyProjectSPRING.entity.TransportCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import com.batanasov.javaTransportCompanyProjectSPRING.entity.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository interface for Employee entities.
 * Extends JpaRepository to provide standard CRUD operations and custom query methods for Employee entities.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Repository interface for Employee entities.
     * Extends JpaRepository to provide standard CRUD operations and custom query methods for Employee entities.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.EmployeeDTO(e.employeeId, e.name, e.position, e.salary, e.qualification, e.company.companyId) " +
            "FROM Employee e WHERE e.qualification = :qualification AND e.salary > :salary")
    List<EmployeeDTO> findByQualificationAndSalaryGreaterThan(@Param("qualification") Qualification qualification, @Param("salary") BigDecimal salary);
    /**
     * Finds employees with a specific position and a salary less than the specified amount.
     *
     * @param position The position to filter by.
     * @param salary The maximum salary threshold.
     * @return List of EmployeeDTOs meeting the criteria.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.EmployeeDTO(e.employeeId, e.name, e.position, e.salary, e.qualification, e.company.companyId) " +
            "FROM Employee e WHERE e.position = :position AND e.salary < :salary")
    List<EmployeeDTO> findByPositionAndSalaryLessThan(@Param("position") String position, @Param("salary") BigDecimal salary);

    /**
     * Finds employees with a specific qualification and a specific salary.
     *
     * @param qualification The qualification to filter by.
     * @param salary The exact salary.
     * @return List of EmployeeDTOs meeting the criteria.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.EmployeeDTO(e.employeeId, e.name, e.position, e.salary, e.qualification, e.company.companyId) " +
            "FROM Employee e WHERE e.qualification = :qualification AND e.salary = :salary")
    List<EmployeeDTO> findByQualificationAndSalary(@Param("qualification") Qualification qualification, @Param("salary") BigDecimal salary);

    /**
     * Finds employees with a specific qualification.
     *
     * @param qualification The qualification to filter by.
     * @return List of EmployeeDTOs with the specified qualification.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.EmployeeDTO(e.employeeId, e.name, e.position, e.salary, e.qualification, e.company.companyId) " +
            "FROM Employee e WHERE e.qualification = :qualification")
    List<EmployeeDTO> findByQualification(@Param("qualification") Qualification qualification);
    /**
     * Finds employees with salaries within a specified range.
     *
     * @param minSalary The minimum salary.
     * @param maxSalary The maximum salary.
     * @return List of EmployeeDTOs with salaries within the specified range.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.EmployeeDTO(e.employeeId, e.name, e.position, e.salary, e.qualification, e.company.companyId) " +
            "FROM Employee e WHERE e.salary BETWEEN :minSalary AND :maxSalary")
    List<EmployeeDTO> findBySalaryBetween(@Param("minSalary") BigDecimal minSalary, @Param("maxSalary") BigDecimal maxSalary);
    /**
     * Finds employees with a specific position.
     *
     * @param position The position to filter by.
     * @return List of EmployeeDTOs with the specified position.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.EmployeeDTO(e.employeeId, e.name, e.position, e.salary, e.qualification, e.company.companyId) " +
            "FROM Employee e WHERE e.position = :position")
    List<EmployeeDTO> findByPosition(@Param("position") String position);
    /**
     * Finds employees working for a specific transport company.
     *
     * @param company The company to filter by.
     * @return List of EmployeeDTOs working for the specified company.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.EmployeeDTO(e.employeeId, e.name, e.position, e.salary, e.qualification, e.company.companyId) " +
            "FROM Employee e WHERE e.company = :company")
    List<EmployeeDTO> findByCompany(@Param("company") TransportCompany company);
    /**
     * Retrieves a list of EmployeeDTOs ordered by name in ascending order.
     *
     * @return List of EmployeeDTOs sorted by name.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.EmployeeDTO(e.employeeId, e.name, e.position, e.salary, e.qualification, e.company.companyId) " +
            "FROM Employee e ORDER BY e.name ASC")
    List<EmployeeDTO> findAllByOrderByNameAsc();

    /**
     * Retrieves a list of EmployeeContractCountDTOs, each representing the number of contracts associated with an employee.
     *
     * @return List of EmployeeContractCountDTOs.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.EmployeeContractCountDTO(e, COUNT(c)) FROM Employee e JOIN e.transportContracts c GROUP BY e")
    List<EmployeeContractCountDTO> findEmployeeContractCounts();
    /**
     * Retrieves a list of EmployeeRevenueDTOs, each representing the total revenue generated by an employee through their associated contracts.
     *
     * @return List of EmployeeRevenueDTOs.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.EmployeeRevenueDTO(e.employeeId, e.name, SUM(c.price)) FROM Employee e JOIN e.transportContracts c GROUP BY e")
    List<EmployeeRevenueDTO> sumRevenueByEmployee();
}
