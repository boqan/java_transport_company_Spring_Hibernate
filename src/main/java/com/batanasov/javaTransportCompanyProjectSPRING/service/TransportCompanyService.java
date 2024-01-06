package com.batanasov.javaTransportCompanyProjectSPRING.service;

import com.batanasov.javaTransportCompanyProjectSPRING.entity.TransportCompany;
import com.batanasov.javaTransportCompanyProjectSPRING.repository.TransportCompanyRepository;
import com.batanasov.javaTransportCompanyProjectSPRING.exceptions.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for handling operations related to transport companies.
 * Includes methods for creating, updating, deleting, and fetching transport company data.
 */
@Service
public class TransportCompanyService {

    private final TransportCompanyRepository transportCompanyRepository;

    private static final Logger logger = LoggerFactory.getLogger(TransportCompanyService.class);

    @Autowired
    public TransportCompanyService(TransportCompanyRepository transportCompanyRepository) {
        this.transportCompanyRepository = transportCompanyRepository;
    }
    /**
     * Creates a new transport company in the database.
     *
     * @param transportCompany the transport company to be created.
     * @return the saved transport company entity.
     */
    @Transactional
    public TransportCompany createTransportCompany(@NotNull TransportCompany transportCompany) {
        logger.info("Creating transport company: {}", transportCompany);
        return transportCompanyRepository.save(transportCompany);
    }

    /**
     * Updates an existing transport company identified by the given ID.
     *
     * @param id the ID of the transport company to update.
     * @param updatedCompany the updated transport company details.
     * @return the updated transport company entity.
     * @throws EntityNotFoundException if the transport company with the given ID is not found.
     */
    @Transactional
    public TransportCompany updateTransportCompany(@NotNull Long id, @NotNull TransportCompany updatedCompany) {
        return transportCompanyRepository.findById(id)
                .map(company -> {
                    company.setName(updatedCompany.getName());
                    company.setAddress(updatedCompany.getAddress());
                    company.setRevenue(updatedCompany.getRevenue());
                    logger.info("Updating transport company: {}", company);
                    // Add more fields as needed
                    return transportCompanyRepository.save(company);
                })
                .orElseThrow(() -> new EntityNotFoundException("TransportCompany not found with id: " + id));
    }

    /**
     * Deletes a transport company identified by the given ID.
     *
     * @param id the ID of the transport company to delete.
     * @throws EntityNotFoundException if the transport company with the given ID is not found.
     */
    @Transactional
    public void deleteTransportCompany(@NotNull Long id) {
        TransportCompany company = transportCompanyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TransportCompany not found with id: " + id));

        logger.info("Deleting transport company: {}", company);
        transportCompanyRepository.delete(company);
    }

    /**
     * Retrieves a transport company by their ID.
     *
     * @param id the ID of the transport company to retrieve.
     * @return an Optional containing the transport company if found, or an empty Optional otherwise.
     */
    @Transactional(readOnly = true)
    public Optional<TransportCompany> getTransportCompany(Long id) {
        return transportCompanyRepository.findById(id);
    }

    /**
     * Retrieves all transport companies.
     *
     * @return a list of all transport companies.
     */
    @Transactional(readOnly = true)
    public List<TransportCompany> getAllTransportCompanies() {
        return transportCompanyRepository.findAll();
    }
}
