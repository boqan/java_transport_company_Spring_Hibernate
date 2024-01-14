package com.batanasov.javaTransportCompanyProjectSPRING.repository;

import com.batanasov.javaTransportCompanyProjectSPRING.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository interface for vehicle entities.
 * Extends JpaRepository to provide standard CRUD operations and query methods for Vehicle entities.
 */
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
