package ua.com.novopacksv.production.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.NotUniqueFieldException;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.orderModel.Client;
import ua.com.novopacksv.production.repository.orderRepository.ClientRepository;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    @Transactional(readOnly = true)
    public Client findById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Client whit id = %d is not found!", id);
            return new ResourceNotFoundException(message);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client save(Client client) {
        checkNameUnique(client);
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        findById(client.getId());
        return save(client);
    }

    @Override
    public void delete(Long id) {
        clientRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Client findOne(String name) {
        return clientRepository.findByName(name).orElseThrow(() -> {
            String message = String.format("Client whit name = %s is not found!", name);
            return new ResourceNotFoundException(message);
        });
    }

    private void checkNameUnique(Client client) {
        Client entityClient = clientRepository.findByName(client.getName()).orElse(null);
        if (entityClient != null && !hasSameId(client, entityClient)) {
            throw new NotUniqueFieldException("Client name must be unique!");
        }
    }

    private boolean hasSameId(Client client, Client entityClient) {
        Long id = client.getId();
        Long entityId = entityClient.getId();
        return id != null && entityId != null && Objects.equals(id, entityId);
    }

}