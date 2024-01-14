package com.batanasov.javaTransportCompanyProjectSPRING.service;

import com.batanasov.javaTransportCompanyProjectSPRING.entity.Client;
import com.batanasov.javaTransportCompanyProjectSPRING.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.batanasov.javaTransportCompanyProjectSPRING.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing clients in the transport system.
 * Provides methods for creating, updating, deleting, and retrieving client information.
 */

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Creates a new client in the system.
     *
     * @param client The client object to be created.
     * @return The saved client object with generated ID.
     */
    @Transactional
    public Client createClient(Client client) {
        Optional<Client> clientOptional = clientRepository.findById(client.getClientId());
        if(clientOptional.isPresent()) {
            throw new EntityNotFoundException("Client with id: " + client.getClientId() + " already exists.");
        }
        logger.info("Creating client: {}", client);
        return clientRepository.save(client);
    }

    /**
     * Updates an existing client's details.
     *
     * @param id The ID of the client to be updated.
     * @param updatedClient The client object with updated details.
     * @return The updated client object.
     * @throws EntityNotFoundException if the client with the specified ID does not exist.
     */
    @Transactional
    public Client updateClient(Long id, Client updatedClient) {
        return clientRepository.findById(id)
                .map(client -> {
                    client.setName(updatedClient.getName());
                    client.setContactDetails(updatedClient.getContactDetails());
                    client.setOutstandingPayments(updatedClient.getOutstandingPayments());
                    client.setContracts(updatedClient.getContracts());
                    logger.info("Updating client: {}", client);
                    return clientRepository.save(client);
                })
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
    }

    /**
     * Updates the payment status of a client.
     *
     * @param clientId The ID of the client whose payment status is to be updated.
     * @param needsToPay The new payment status.
     * @throws EntityNotFoundException if the client with the specified ID does not exist.
     */
    @Transactional
    public void updatePaymentStatus(Long clientId, boolean needsToPay) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));
        client.setNeedsToPay(needsToPay);
        clientRepository.save(client);
    }


    /**
     * Deletes a client from the system.
     *
     * @param id The ID of the client to be deleted.
     * @throws EntityNotFoundException if the client with the specified ID does not exist.
     */
    @Transactional
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));

        logger.info("Deleting client: {}", client);
        clientRepository.delete(client);
    }

    /**
     * Retrieves a client by their ID.
     *
     * @param id The ID of the client to be retrieved.
     * @return An Optional containing the found client or an empty Optional if not found.
     */
    @Transactional(readOnly = true)
    public Optional<Client> getClient(Long id) {
        return clientRepository.findById(id);
    }

    /**
     * Retrieves all clients in the system.
     *
     * @return A list of all clients.
     */
    @Transactional(readOnly = true)
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

}
