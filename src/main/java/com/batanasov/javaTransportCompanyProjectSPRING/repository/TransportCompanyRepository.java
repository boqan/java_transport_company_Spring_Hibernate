package com.batanasov.javaTransportCompanyProjectSPRING.repository;

import com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportCompanyDTO;
import com.batanasov.javaTransportCompanyProjectSPRING.entity.TransportCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransportCompanyRepository extends JpaRepository<TransportCompany, Long> {
    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportCompanyDTO(tc.companyId, tc.name, tc.address, tc.revenue) FROM TransportCompany tc ORDER BY tc.revenue DESC, tc.name ASC")
    List<TransportCompanyDTO> findAllByOrderByRevenueDescNameAsc();

    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportCompanyDTO(tc.companyId, tc.name, tc.address, tc.revenue) FROM TransportCompany tc WHERE tc.revenue >= :minRevenue")
    List<TransportCompanyDTO> findByRevenueGreaterThanEqual(@Param("minRevenue") Double minRevenue);

    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportCompanyDTO(tc.companyId, tc.name, tc.address, tc.revenue) FROM TransportCompany tc WHERE tc.address LIKE %:address%")
    List<TransportCompanyDTO> findByAddressContaining(@Param("address") String address);

    @Query("SELECT new com.batanasov.javaTransportCompanyProjectSPRING.DTO.TransportCompanyDTO(tc.companyId, tc.name, tc.address, tc.revenue) FROM TransportCompany tc ORDER BY tc.name DESC")
    List<TransportCompanyDTO> findAllByOrderByNameDesc();


}
