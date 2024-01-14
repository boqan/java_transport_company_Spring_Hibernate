package com.batanasov.javaTransportCompanyProjectSPRING.repository;

import com.batanasov.javaTransportCompanyProjectSPRING.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository interface for client entities.
 * Extends JpaRepository to provide standard CRUD operations and query methods for Client entities.
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
}
