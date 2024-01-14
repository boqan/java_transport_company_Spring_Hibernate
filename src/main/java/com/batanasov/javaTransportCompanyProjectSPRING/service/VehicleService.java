package com.batanasov.javaTransportCompanyProjectSPRING.service;

import com.batanasov.javaTransportCompanyProjectSPRING.entity.Vehicle;
import com.batanasov.javaTransportCompanyProjectSPRING.exceptions.EntityAlreadyExistsException;
import com.batanasov.javaTransportCompanyProjectSPRING.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.batanasov.javaTransportCompanyProjectSPRING.repository.VehicleRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing vehicles.
 * Includes methods for creating, updating, deleting, and retrieving vehicle information.
 */
@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    private static final Logger logger = LoggerFactory.getLogger(VehicleService.class);

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Creates a new vehicle in the database.
     *
     * @param vehicle the vehicle to be created.
     * @return the saved vehicle entity.
     * @throws EntityAlreadyExistsException if a vehicle with the same ID already exists.
     */
    @Transactional
    public Vehicle createVehicle(Vehicle vehicle) throws EntityNotFoundException {
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(savedVehicle.getVehicleId());
        if(vehicleOptional.isPresent() && !vehicleOptional.get().equals(savedVehicle)) {
            vehicleRepository.delete(savedVehicle);
            throw new EntityAlreadyExistsException("Vehicle with id: " + savedVehicle.getVehicleId() + " already exists.");
        }

        logger.info("Created vehicle: {}", savedVehicle);
        return savedVehicle;
    }

    /**
     * Updates the details of an existing vehicle.
     *
     * @param id the ID of the vehicle to be updated.
     * @param updatedVehicle the vehicle entity with updated details.
     * @return the updated vehicle entity.
     * @throws EntityNotFoundException if the vehicle with the specified ID does not exist.
     */
    @Transactional
    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) throws EntityNotFoundException {
        return vehicleRepository.findById(id)
                .map(vehicle -> {
                    vehicle.setType(updatedVehicle.getType());
                    vehicle.setLicensePlate(updatedVehicle.getLicensePlate());
                    vehicle.setCapacity(updatedVehicle.getCapacity());
                    vehicle.setCompany(updatedVehicle.getCompany());
                    logger.info("Updating vehicle: {}", vehicle);
                    return vehicleRepository.save(vehicle);
                })
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with id: " + id));
    }

    /**
     * Deletes a vehicle from the database.
     *
     * @param id the ID of the vehicle to be deleted.
     * @throws EntityNotFoundException if the vehicle with the specified ID does not exist.
     */
    @Transactional
    public void deleteVehicle(Long id) throws EntityNotFoundException {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with id: " + id));

        logger.info("Deleting vehicle: {}", vehicle);
        vehicleRepository.delete(vehicle);
    }

    /**
     * Retrieves a vehicle by its ID.
     *
     * @param id the ID of the vehicle to be retrieved.
     * @return an Optional containing the vehicle if found, or an empty Optional otherwise.
     */
    @Transactional(readOnly = true)
    public Optional<Vehicle> getVehicle(Long id) {
        return vehicleRepository.findById(id);
    }

    /**
     * Retrieves all vehicles from the database.
     *
     * @return a list of all vehicles.
     */
    @Transactional(readOnly = true)
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
}
