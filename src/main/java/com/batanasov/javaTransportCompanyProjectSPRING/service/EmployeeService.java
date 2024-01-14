package com.batanasov.javaTransportCompanyProjectSPRING.service;

import com.batanasov.javaTransportCompanyProjectSPRING.DTO.EmployeeContractCountDTO;
import com.batanasov.javaTransportCompanyProjectSPRING.DTO.EmployeeRevenueDTO;
import com.batanasov.javaTransportCompanyProjectSPRING.entity.Employee;
import com.batanasov.javaTransportCompanyProjectSPRING.exceptions.EntityAlreadyExistsException;
import com.batanasov.javaTransportCompanyProjectSPRING.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.batanasov.javaTransportCompanyProjectSPRING.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing employees in the transport company.
 * Offers functionality to create, update, delete, and retrieve employee details, as well as reporting on contracts and revenue.
 */

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Creates a new employee in the database.
     *
     * @param employee the employee to be created.
     * @return the saved employee entity.
     * @throws EntityNotFoundException if an employee with the same ID already exists.
     */
    @Transactional
    public Employee createEmployee(Employee employee) throws EntityNotFoundException {
        Employee savedEmployee = employeeRepository.save(employee);

        Optional<Employee> employeeOptional = employeeRepository.findById(savedEmployee.getEmployeeId());
        if(employeeOptional.isPresent() && !employeeOptional.get().equals(savedEmployee)) {
            employeeRepository.delete(savedEmployee);
            throw new EntityAlreadyExistsException("Employee with id: " + savedEmployee.getEmployeeId() + " already exists.");
        }

        logger.info("Created employee: {}", savedEmployee);
        return savedEmployee;
    }

    /**
     * Updates an existing employee identified by the given ID.
     *
     * @param id the ID of the employee to update.
     * @param updatedEmployee the updated employee details.
     * @return the updated employee entity.
     * @throws EntityNotFoundException if the employee with the given ID is not found.
     */
    @Transactional
    public Employee updateEmployee(Long id, Employee updatedEmployee) throws EntityNotFoundException {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(updatedEmployee.getName());
                    employee.setPosition(updatedEmployee.getPosition());
                    employee.setSalary(updatedEmployee.getSalary());
                    employee.setQualification(updatedEmployee.getQualification());
                    employee.setCompany(updatedEmployee.getCompany());
                    employee.setTransportContracts(updatedEmployee.getTransportContracts());
                    logger.info("Updating employee: {}", employee);
                    return employeeRepository.save(employee);
                })
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
    }

    /**
     * Deletes an employee identified by the given ID.
     *
     * @param id the ID of the employee to delete.
     * @throws EntityNotFoundException if the employee with the given ID is not found.
     */
    @Transactional
    public void deleteEmployee(Long id) throws EntityNotFoundException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        logger.info("Deleting employee: {}", employee);
        employeeRepository.delete(employee);
    }

    /**
     * Retrieves an employee by their ID.
     *
     * @param id the ID of the employee to retrieve.
     * @return an Optional containing the employee if found, or an empty Optional otherwise.
     */
    @Transactional(readOnly = true)
    public Optional<Employee> getEmployee(Long id) {
        return employeeRepository.findById(id);
    }

    /**
     * Retrieves all employees.
     *
     * @return a list of all employees.
     */
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Retrieves the number of contracts associated with each employee.
     *
     * @return a list of EmployeeContractCountDTO containing the count of contracts for each employee.
     */
    @Transactional(readOnly = true)
    public List<EmployeeContractCountDTO> getEmployeeContractCounts() {
        return employeeRepository.findEmployeeContractCounts();
    }

    /**
     * Calculates the total revenue generated by each employee through the contracts they are associated with.
     *
     * @return a list of EmployeeRevenueDTO containing the revenue generated by each employee.
     */
    public List<EmployeeRevenueDTO> getEmployeeRevenue() {
        return employeeRepository.sumRevenueByEmployee();
    }


}
