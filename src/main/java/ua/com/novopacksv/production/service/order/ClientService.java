package ua.com.novopacksv.production.service.order;

import ua.com.novopacksv.production.model.orderModel.Client;
import ua.com.novopacksv.production.service.BaseEntityService;

import java.util.List;

public interface ClientService extends BaseEntityService<Client> {

    List<Client> findAll(String sort);

}