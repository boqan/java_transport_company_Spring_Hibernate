package com.batanasov.javaTransportCompanyProjectSPRING.service;

import com.batanasov.javaTransportCompanyProjectSPRING.Enums.Qualification;
import com.batanasov.javaTransportCompanyProjectSPRING.entity.Employee;
import com.batanasov.javaTransportCompanyProjectSPRING.entity.TransportContract;
import com.batanasov.javaTransportCompanyProjectSPRING.entity.Vehicle;
import com.batanasov.javaTransportCompanyProjectSPRING.exceptions.EmployeeNotQualifiedForVehicleException;
import com.batanasov.javaTransportCompanyProjectSPRING.exceptions.EntityAlreadyExistsException;
import com.batanasov.javaTransportCompanyProjectSPRING.exceptions.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.batanasov.javaTransportCompanyProjectSPRING.repository.TransportContractRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Service class for managing transport contracts.
 * Provides methods for creating, updating, deleting, and retrieving transport contracts, including reporting and CSV file operations.
 */
@Service
public class TransportContractService {

    private final TransportContractRepository transportContractRepository;

    private static final Logger logger = LoggerFactory.getLogger(TransportContractService.class);

    @Autowired
    public TransportContractService(TransportContractRepository transportContractRepository) {
        this.transportContractRepository = transportContractRepository;
    }

    /**
     * Checks if an employee is qualified to operate a specific vehicle.
     *
     * @param employee the employee whose qualifications are to be checked.
     * @param vehicle the vehicle to be operated by the employee.
     * @return true if the employee is qualified for the vehicle, false otherwise.
     */
    public boolean isQualifiedForVehicle(Employee employee, Vehicle vehicle) {
        // Check if employee or vehicle is null
        if (employee == null || vehicle == null) {
            return false;
        }

        // Retrieve the qualification of the employee
        Qualification qualification = employee.getQualification();

        // Logic to determine if the employee's qualification matches the vehicle's type
        return switch (vehicle.getType()) {
            case TRUCK ->
                // TRUCK requires HEAVY_VEHICLES qualification
                    qualification == Qualification.HEAVY_VEHICLES;
            case BUS ->
                // BUS requires PASSENGER_TRANSPORT qualification
                    qualification == Qualification.PASSENGER_TRANSPORT;
            case TANKER ->
                // TANKER requires HAZARDOUS_MATERIAL qualification
                    qualification == Qualification.HAZARDOUS_MATERIAL;
            case VAN ->
                // VAN requires no special qualification or check for a specific one
                    true; // No qualification required
        };
    }


    /**
     * Creates a new transport contract in the database after verifying employee qualifications.
     *
     * @param transportContract the transport contract to be created.
     * @return the saved transport contract entity.
     * @throws EmployeeNotQualifiedForVehicleException if the employee is not qualified for the vehicle in the contract.
     * @throws EntityNotFoundException if a transport contract with the same ID already exists.
     */
    @Transactional
    public TransportContract createTransportContract(@NotNull TransportContract transportContract)
            throws EmployeeNotQualifiedForVehicleException, EntityNotFoundException {

        if (!isQualifiedForVehicle(transportContract.getEmployee(), transportContract.getVehicle())) {
            throw new EmployeeNotQualifiedForVehicleException("Employee does not have the required qualification for this vehicle type.");
        }

        TransportContract savedContract = transportContractRepository.save(transportContract);

        Optional<TransportContract> transportContractOptional = transportContractRepository.findById(savedContract.getContractId());
        if(transportContractOptional.isPresent() && !transportContractOptional.get().equals(savedContract)) {
            transportContractRepository.delete(savedContract);
            throw new EntityAlreadyExistsException("TransportContract with id: " + savedContract.getContractId() + " already exists.");
        }

        logger.info("Created transport contract: {}", savedContract);
        return savedContract;
    }

    /**
     * Updates an existing transport contract identified by the given ID.
     *
     * @param id the ID of the transport contract to update.
     * @param updatedContract the updated transport contract details.
     * @return the updated transport contract entity.
     * @throws EmployeeNotQualifiedForVehicleException if the employee is not qualified for the vehicle in the contract.
     * @throws EntityNotFoundException if the transport contract with the given ID is not found.
     */
    @Transactional
    public TransportContract updateTransportContract(@NotNull Long id, @NotNull TransportContract updatedContract) throws EmployeeNotQualifiedForVehicleException, EntityNotFoundException {
        if (!isQualifiedForVehicle(updatedContract.getEmployee(), updatedContract.getVehicle())) {
            throw new EmployeeNotQualifiedForVehicleException("Employee does not have the required qualification for this vehicle type.");
        }

        return transportContractRepository.findById(id)
                .map(contract -> {
                    contract.setContractId(updatedContract.getContractId());
                    contract.setDestination(updatedContract.getDestination());
                    contract.setStartDate(updatedContract.getStartDate());
                    contract.setPrice(updatedContract.getPrice());
                    contract.setCargoDetails(updatedContract.getCargoDetails());
                    contract.setStatus(updatedContract.getStatus());
                    contract.setWeight(updatedContract.getWeight());
                    contract.setCompany(updatedContract.getCompany());
                    contract.setClient(updatedContract.getClient());
                    contract.setVehicle(updatedContract.getVehicle());
                    contract.setEmployee(updatedContract.getEmployee());
                    logger.info("Updating transport contract: {}", contract);
                    // Add more fields as needed
                    return transportContractRepository.save(contract);
                })
                .orElseThrow(() -> new EntityNotFoundException("TransportContract not found with id: " + id));
    }

    /**
     * Deletes a transport contract identified by the given ID.
     *
     * @param id the ID of the transport contract to delete.
     * @throws EntityNotFoundException if the transport contract with the given ID is not found.
     */
    @Transactional
    public void deleteTransportContract(@NotNull Long id) throws EntityNotFoundException {
        TransportContract contract = transportContractRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TransportContract not found with id: " + id));

        logger.info("Deleting transport contract: {}", contract);
        transportContractRepository.delete(contract);
    }

    /**
     * Retrieves all transport contracts.
     *
     * @return a list of all transport contracts.
     */
    @Transactional(readOnly = true)
    public Optional<TransportContract> getTransportContract(Long id) {
        return transportContractRepository.findById(id);
    }

    /**
     * Retrieves all transport contracts.
     *
     * @return a list of all transport contracts.
     */
    @Transactional(readOnly = true)
    public List<TransportContract> getAllTransportContracts() {
        return transportContractRepository.findAll();
    }


    /**
     * Retrieves the total number of transport contracts in the database.
     *
     * @return the total count of transport contracts.
     */
    public long getTotalNumberOfTransportContracts() {
        return transportContractRepository.count();
    }

    /**
     * Calculates the total revenue generated from all transport contracts.
     *
     * @return the sum of prices of all transport contracts.
     */
    public double getTotalRevenueFromTransportContracts() {
        return transportContractRepository.sumAllContractPrices();
    }

    /**
     * Calculates the total revenue generated by the company within a specified period.
     *
     * @param startDate the start date of the period.
     * @param endDate the end date of the period.
     * @return the total revenue generated in the specified period.
     */
    // I implemented this with only one date, since there isn't an endDate field in the Entity, but it still works
    public Double getCompanyRevenueForPeriod(LocalDate startDate, LocalDate endDate) {
        return transportContractRepository.sumContractPricesForPeriod(startDate, endDate);
    }

    /**
     * Exports transport contracts to a CSV file.
     *
     * @param filename the name of the file to which the data will be exported.
     */
    //reading and writing CSV to files
    public void exportTransportContractsToCSV(String filename) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("ContractId,Destination,StartDate,Price,CargoDetails,Status,Weight\n"); // CSV header
            List<TransportContract> contracts = transportContractRepository.findAll();
            for (TransportContract contract : contracts) {
                String startDateStr = (contract.getStartDate() != null) ? contract.getStartDate().format(dateFormat) : "N/A";
                writer.write(String.format(Locale.US,"%d,%s,%s,%.2f,%s,%b,%.2f\n",
                        contract.getContractId(),
                        contract.getDestination(),
                        startDateStr,
                        contract.getPrice(),
                        contract.getCargoDetails(),
                        contract.getStatus(),
                        contract.getWeight()));
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    /**
     * Imports transport contracts from a CSV file.
     *
     * @param filename the name of the file from which the data will be imported.
     * @return a list of transport contracts read from the file.
     */
    public List<TransportContract> importTransportContractsFromCSV(String filename) {
        List<TransportContract> contracts = new ArrayList<>();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    TransportContract contract = new TransportContract();
                    contract.setContractId(Long.parseLong(parts[0]));
                    contract.setDestination(parts[1]);
                    contract.setStartDate(!parts[2].equals("N/A") ? LocalDate.parse(parts[2], dateFormat) : null);
                    contract.setPrice(Double.parseDouble(parts[3]));
                    contract.setCargoDetails(parts[4]);
                    contract.setStatus(Boolean.parseBoolean(parts[5]));
                    contract.setWeight(Double.parseDouble(parts[6]));
                    contracts.add(contract);
                } catch (DateTimeParseException | NumberFormatException e) {
                    System.err.println("Error parsing line: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from CSV file: " + e.getMessage());
        }

        return contracts;
    }

}
