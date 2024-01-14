package com.batanasov.javaTransportCompanyProjectSPRING.repository;

import com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportCompanyDTO;
import com.batanasov.javaTransportCompanyProjectSPRING.entity.TransportCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 * Repository interface for TransportCompany entities.
 * Extends JpaRepository to provide standard CRUD operations and custom query methods for TransportCompany entities.
 */
public interface TransportCompanyRepository extends JpaRepository<TransportCompany, Long> {
    /**
     * Retrieves a list of TransportCompanyDTOs ordered by revenue in descending order and then by name in ascending order.
     *
     * @return List of TransportCompanyDTOs sorted by revenue and name.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportCompanyDTO(tc.companyId, tc.name, tc.address, tc.revenue) FROM TransportCompany tc ORDER BY tc.revenue DESC, tc.name ASC")
    List<TransportCompanyDTO> findAllByOrderByRevenueDescNameAsc();
    /**
     * Finds TransportCompanyDTOs with revenue greater than or equal to the specified minimum revenue.
     *
     * @param minRevenue The minimum revenue threshold.
     * @return List of TransportCompanyDTOs meeting the revenue criteria.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportCompanyDTO(tc.companyId, tc.name, tc.address, tc.revenue) FROM TransportCompany tc WHERE tc.revenue >= :minRevenue")
    List<TransportCompanyDTO> findByRevenueGreaterThanEqual(@Param("minRevenue") Double minRevenue);

    /**
     * Finds TransportCompanyDTOs where the address contains the specified string.
     *
     * @param address The string to search within the address.
     * @return List of TransportCompanyDTOs with addresses containing the specified string.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportCompanyDTO(tc.companyId, tc.name, tc.address, tc.revenue) FROM TransportCompany tc WHERE tc.address LIKE %:address%")
    List<TransportCompanyDTO> findByAddressContaining(@Param("address") String address);
    /**
     * Retrieves a list of TransportCompanyDTOs ordered by name in descending order.
     *
     * @return List of TransportCompanyDTOs sorted by name in descending order.
     */
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportCompanyDTO(tc.companyId, tc.name, tc.address, tc.revenue) FROM TransportCompany tc ORDER BY tc.name DESC")
    List<TransportCompanyDTO> findAllByOrderByNameDesc();


}
