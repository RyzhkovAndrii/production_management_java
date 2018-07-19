package ua.com.novopacksv.production.service.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.NotUniqueFieldException;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.orderModel.Client;
import ua.com.novopacksv.production.repository.orderRepository.ClientRepository;

import java.util.List;
import java.util.Objects;

/**
 * Class implements interface {@link ClientService} and contains CRUD methods
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    /**
     * An object of repository layer for have access to methods of work with DB
     */
    private final ClientRepository clientRepository;

    /**
     * Method finds a client by it's id
     *
     * @param id - client's id
     * @return a client
     * @throws ResourceNotFoundException if client with pointed id does not exist
     */
    @Override
    @Transactional(readOnly = true)
    public Client findById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Client whit id = %d is not found!", id);
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findById(Long id): Client with id = {} was found: {}", id, client);
        return client;
    }

    /**
     * Method finds all clients
     *
     * @return list of clients
     */
    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        log.debug("Method findAll(): All clients are finding");
        return clientRepository.findAll();
    }

    /**
     * Method saves a client after checking if its name is unique
     *
     * @param client - new client for save
     * @return saved client
     */
    @Override
    public Client save(Client client) {
        checkNameUnique(client);
        log.debug("Method save(Client client): Client {} was saved", client);
        return clientRepository.save(client);
    }

    /**
     * Method updates a client
     *
     * @param client - client for update
     * @return updated client
     * @throws ResourceNotFoundException if client with such id does not exist
     */
    @Override
    public Client update(Client client) throws ResourceNotFoundException {
        findById(client.getId());
        log.debug("Method update(Client client): Client {} was updated", client);
        return save(client);
    }

    /**
     * Method delete a client by it's id
     *
     * @param id - client's id
     * @throws ResourceNotFoundException if a client with pointed id does not exist
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        clientRepository.delete(findById(id));
        log.debug("Method delete(Long id): Client with id = {} was deleted", id);
    }

    /**
     * Method find a client by it's name
     *
     * @param name - client's name
     * @return a client
     */
    @Override
    @Transactional(readOnly = true)
    public Client findOne(String name) {
        Client client = clientRepository.findByName(name).orElseThrow(() -> {
            String message = String.format("Client whit name = %s is not found!", name);
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findOne(String name): Client with name {} was found: {}", name, client);
        return client;
    }

    /**
     * Method checks if the name of a new client is unique
     *
     * @param client - client for check
     */
    private void checkNameUnique(Client client) {
        Client entityClient = clientRepository.findByName(client.getName()).orElse(null);
        if (entityClient != null && !hasSameId(client, entityClient)) {
            throw new NotUniqueFieldException("Client name must be unique!");
        }
        log.debug("Method checkNameUnique(Client client): The name for client {} is unique", client);
    }

    /**
     * Method checks if two clients have the same id
     *
     * @param client       - first client
     * @param entityClient - second client
     * @return true if clients have the same id
     */
    private boolean hasSameId(Client client, Client entityClient) {
        Long id = client.getId();
        Long entityId = entityClient.getId();
        return id != null && entityId != null && Objects.equals(id, entityId);
    }

}