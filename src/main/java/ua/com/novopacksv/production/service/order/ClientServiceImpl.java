package ua.com.novopacksv.production.service.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.orderModel.Client;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Override
    public Client findById(Long id) {
        return null;
    }

    @Override
    public List<Client> findAll() {
        return null;
    }

    @Override
    public Client save(Client client) {
        return null;
    }

    @Override
    public Client update(Client client) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

}