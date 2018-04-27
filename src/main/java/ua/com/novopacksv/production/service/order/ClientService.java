package ua.com.novopacksv.production.service.order;

import ua.com.novopacksv.production.model.orderModel.Client;
import ua.com.novopacksv.production.service.BaseEntityService;

public interface ClientService extends BaseEntityService<Client> {

    Client findOne(String name);

}