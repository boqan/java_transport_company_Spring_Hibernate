package com.batanasov.javaTransportCompanyProjectSPRING.repository;

import com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportContractDTO;
import com.batanasov.javaTransportCompanyProjectSPRING.entity.TransportContract;
import com.batanasov.javaTransportCompanyProjectSPRING.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
/**
 * Repository interface for TransportContract entities.
 * Extends JpaRepository to provide standard CRUD operations and custom query methods for TransportContract entities.
 */
public interface TransportContractRepository extends JpaRepository<TransportContract, Long> {

    /**
     * Finds transport contracts by destination.
     *
     * @param destination The destination to filter by.
     * @return List of TransportContractDTOs with the specified destination.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportContractDTO(tc.contractId, tc.destination, tc.startDate, tc.price, tc.cargoDetails, tc.status, tc.weight, tc.company.companyId, tc.client.clientId, tc.vehicle.vehicleId, tc.employee.employeeId) FROM TransportContract tc WHERE tc.destination = :destination")
    List<TransportContractDTO> findByDestination(@Param("destination") String destination);

    /**
     * Finds transport contracts containing specific cargo details.
     *
     * @param cargoDetails The cargo details to filter by.
     * @return List of TransportContractDTOs containing the specified cargo details.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportContractDTO(tc.contractId, tc.destination, tc.startDate, tc.price, tc.cargoDetails, tc.status, tc.weight, tc.company.companyId, tc.client.clientId, tc.vehicle.vehicleId, tc.employee.employeeId) FROM TransportContract tc WHERE tc.cargoDetails LIKE %:cargoDetails%")
    List<TransportContractDTO> findByCargoDetailsContaining(@Param("cargoDetails") String cargoDetails);

    /**
     * Finds transport contracts with prices within a specified range.
     *
     * @param minPrice The minimum price.
     * @param maxPrice The maximum price.
     * @return List of TransportContractDTOs with prices within the specified range.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportContractDTO(tc.contractId, tc.destination, tc.startDate, tc.price, tc.cargoDetails, tc.status, tc.weight, tc.company.companyId, tc.client.clientId, tc.vehicle.vehicleId, tc.employee.employeeId) FROM TransportContract tc WHERE tc.price BETWEEN :minPrice AND :maxPrice")
    List<TransportContractDTO> findByPriceBetween(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    /**
     * Finds transport contracts by status.
     *
     * @param status The status to filter by.
     * @return List of TransportContractDTOs with the specified status.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportContractDTO(tc.contractId, tc.destination, tc.startDate, tc.price, tc.cargoDetails, tc.status, tc.weight, tc.company.companyId, tc.client.clientId, tc.vehicle.vehicleId, tc.employee.employeeId) FROM TransportContract tc WHERE tc.status = :status")
    List<TransportContractDTO> findByStatus(@Param("status") Boolean status);

    /**
     * Finds transport contracts associated with a specific client.
     *
     * @param client The client to filter by.
     * @return List of TransportContractDTOs associated with the specified client.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportContractDTO(tc.contractId, tc.destination, tc.startDate, tc.price, tc.cargoDetails, tc.status, tc.weight, tc.company.companyId, tc.client.clientId, tc.vehicle.vehicleId, tc.employee.employeeId) FROM TransportContract tc WHERE tc.client = :client")
    List<TransportContractDTO> findByClient(@Param("client") Client client);

    /**
     * Retrieves a list of TransportContractDTOs ordered by price in ascending order.
     *
     * @return List of TransportContractDTOs sorted by price.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportContractDTO(tc.contractId, tc.destination, tc.startDate, tc.price, tc.cargoDetails, tc.status, tc.weight, tc.company.companyId, tc.client.clientId, tc.vehicle.vehicleId, tc.employee.employeeId) FROM TransportContract tc ORDER BY tc.price ASC")
    List<TransportContractDTO> findAllByOrderByPriceAsc();


    /**
     * Calculates the total revenue from all transport contracts.
     *
     * @return Total revenue from all contracts.
     */
    @Query("SELECT SUM(c.price) FROM TransportContract c")
    double sumAllContractPrices();

    /**
     * Calculates the total revenue from transport contracts within a specified period.
     *
     * @param startDate The start date of the period.
     * @param endDate The end date of the period.
     * @return Total revenue from contracts within the specified period.
     */
    @Query("SELECT SUM(c.price) FROM TransportContract c WHERE c.startDate BETWEEN :startDate AND :endDate")
    Double sumContractPricesForPeriod(LocalDate startDate, LocalDate endDate);


}
