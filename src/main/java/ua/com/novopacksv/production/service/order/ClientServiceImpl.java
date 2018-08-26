package ua.com.novopacksv.production.service.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.NotUniqueFieldException;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.orderModel.Client;
import ua.com.novopacksv.production.repository.orderRepository.ClientRepository;

import java.util.List;
import java.util.Objects;

/**
 * Class implements interface {@link ClientService} and contains CRUD methods, method for find sorted by parameter
 * and two private methods for check if the Client's name is unique
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    /**
     * An object of repository's layer for have access to Client from db
     */
    private final ClientRepository clientRepository;

    /**
     * Method finds a Client by id
     *
     * @param id - Client's id
     * @return Client from db by id
     * @throws ResourceNotFoundException if Client with this id does not exist in db
     */
    @Override
    @Transactional(readOnly = true)
    public Client findById(Long id) throws ResourceNotFoundException {
        Client client = clientRepository.findById(id).orElseThrow(() -> {
            log.error("Method findById(Long id): Client with id = {} does not exist in db", id);
            String message = String.format("Client whit id = %d is not found!", id);
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findById(Long id): Client with id = {} was found: {}", id, client);
        return client;
    }

    /**
     * Method finds all existed in db Clients
     *
     * @return List of Clients
     */
    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        log.debug("Method findAll(): List<Client> is finding");
        return clientRepository.findAll();
    }

    /**
     * Method saves a new Client with unique name to db
     *
     * @param client - new Client
     * @return saved new Client
     * @throws NotUniqueFieldException if Client's name is not unique
     */
    @Override
    public Client save(Client client) throws NotUniqueFieldException {
        checkNameUnique(client);
        Client clientSaved = clientRepository.save(client);
        log.debug("Method List<Client>: Client {} was saved", clientSaved);
        return clientSaved;
    }

    /**
     * Method saves existed in db Client, calls method save(Client client)
     *
     * @param client - Client for update
     * @return saved Client
     * @throws ResourceNotFoundException if Client with this id does not exist in db
     * @throws NotUniqueFieldException   if Client's name is not unique
     */
    @Override
    public Client update(Client client) throws ResourceNotFoundException, NotUniqueFieldException {
        findById(client.getId());
        Client clientSaved = save(client);
        log.debug("Method update(Client client): Client {} was updated", clientSaved);
        return clientSaved;
    }

    /**
     * Method deletes existed Client from db
     *
     * @param id - Client's id
     * @throws ResourceNotFoundException if Client with this id does not exist in db
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        clientRepository.delete(findById(id));
        log.debug("Method delete(Long id): Client with id = {} was deleted from db");
    }

    /**
     * Method finds all Clients from db and sorts its by pointed parameter
     *
     * @param sortProperties - parameter for sort
     * @return List of Clients
     */
    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll(String sortProperties) {
        Sort sort = new Sort(Sort.Direction.ASC, sortProperties);
        log.debug("Method findAll(String sortProperties): List<Client> is finding and sorting by parameter: {}",
                sortProperties);
        return clientRepository.findAll(sort);
    }

    /**
     * Method tests if the Client's name is unique
     *
     * @param client - Client
     * @throws NotUniqueFieldException if Client's name is not unique
     */
    private void checkNameUnique(Client client) throws NotUniqueFieldException {
        Client entityClient = clientRepository.findByName(client.getName()).orElse(null);
        if (entityClient != null && !hasSameId(client, entityClient)) {
            log.error("Method checkNameUnique(Client client): Name for Client {} is not unique", client);
            throw new NotUniqueFieldException("Client name must be unique!");
        }
        log.debug("Method checkNameUnique(Client client): Name for Client {} is unique", client);
    }

    /**
     * Method tests if two Clients has the same id and equals
     *
     * @param client       - first Client
     * @param entityClient - second Client
     * @return true if two Clients are the same and false if not
     */
    private boolean hasSameId(Client client, Client entityClient) {
        Long id = client.getId();
        Long entityId = entityClient.getId();
        Boolean result = id != null && entityId != null && Objects.equals(id, entityId);
        log.debug("Method hasSameId(Client client, Client entityClient): For Client1 {} and Client2 {} the equals " +
                "was determined as {}", client, entityClient, result);
        return result;
    }

}