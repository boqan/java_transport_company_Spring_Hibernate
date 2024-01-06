package com.batanasov.javaTransportCompanyProjectSPRING.repository;

import com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportContractDTO;
import com.batanasov.javaTransportCompanyProjectSPRING.entity.TransportContract;
import com.batanasov.javaTransportCompanyProjectSPRING.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransportContractRepository extends JpaRepository<TransportContract, Long> {

    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportContractDTO(tc.contractId, tc.destination, tc.startDate, tc.price, tc.cargoDetails, tc.status, tc.weight, tc.company.companyId, tc.client.clientId, tc.vehicle.vehicleId, tc.employee.employeeId) FROM TransportContract tc WHERE tc.destination = :destination")
    List<TransportContractDTO> findByDestination(@Param("destination") String destination);


    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportContractDTO(tc.contractId, tc.destination, tc.startDate, tc.price, tc.cargoDetails, tc.status, tc.weight, tc.company.companyId, tc.client.clientId, tc.vehicle.vehicleId, tc.employee.employeeId) FROM TransportContract tc WHERE tc.cargoDetails LIKE %:cargoDetails%")
    List<TransportContractDTO> findByCargoDetailsContaining(@Param("cargoDetails") String cargoDetails);


    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportContractDTO(tc.contractId, tc.destination, tc.startDate, tc.price, tc.cargoDetails, tc.status, tc.weight, tc.company.companyId, tc.client.clientId, tc.vehicle.vehicleId, tc.employee.employeeId) FROM TransportContract tc WHERE tc.price BETWEEN :minPrice AND :maxPrice")
    List<TransportContractDTO> findByPriceBetween(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);


    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportContractDTO(tc.contractId, tc.destination, tc.startDate, tc.price, tc.cargoDetails, tc.status, tc.weight, tc.company.companyId, tc.client.clientId, tc.vehicle.vehicleId, tc.employee.employeeId) FROM TransportContract tc WHERE tc.status = :status")
    List<TransportContractDTO> findByStatus(@Param("status") Boolean status);


    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportContractDTO(tc.contractId, tc.destination, tc.startDate, tc.price, tc.cargoDetails, tc.status, tc.weight, tc.company.companyId, tc.client.clientId, tc.vehicle.vehicleId, tc.employee.employeeId) FROM TransportContract tc WHERE tc.client = :client")
    List<TransportContractDTO> findByClient(@Param("client") Client client);


    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportContractDTO(tc.contractId, tc.destination, tc.startDate, tc.price, tc.cargoDetails, tc.status, tc.weight, tc.company.companyId, tc.client.clientId, tc.vehicle.vehicleId, tc.employee.employeeId) FROM TransportContract tc ORDER BY tc.price ASC")
    List<TransportContractDTO> findAllByOrderByPriceAsc();


    // izpolzvame tozi metod za da sumirame vsichki ceni na vsichki dogovori i da smetnem obshtiq prihod

    @Query("SELECT SUM(c.price) FROM TransportContract c")
    double sumAllContractPrices();

    // tova se polzva za da se smetnat prihodite za daden period, tui kato nqmame endDate v Entity kato field,
    // se polzva samo startDate na dadeniq Contract
    @Query("SELECT SUM(c.price) FROM TransportContract c WHERE c.startDate BETWEEN :startDate AND :endDate")
    Double sumContractPricesForPeriod(LocalDate startDate, LocalDate endDate);


}
