package ua.com.novopacksv.production.converter.order;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.order.ClientRequest;
import ua.com.novopacksv.production.model.orderModel.Client;

@Component
public class ClientRequestToClientConverter implements Converter<ClientRequest, Client> {

    @Override
    public Client convert(ClientRequest source) { // todo null check
        Client result = new Client();
        result.setName(source.getName());
        return result;
    }
    //todo badRequest exception
}