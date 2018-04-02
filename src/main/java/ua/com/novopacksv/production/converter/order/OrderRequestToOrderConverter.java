package ua.com.novopacksv.production.converter.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.order.OrderRequest;
import ua.com.novopacksv.production.model.orderModel.Client;
import ua.com.novopacksv.production.model.orderModel.Order;
import ua.com.novopacksv.production.service.order.ClientService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class OrderRequestToOrderConverter implements Converter<OrderRequest, Order> {

    @Autowired
    private ClientService clientService;

    @Override
    public Order convert(OrderRequest source) {
        Order result = new Order();
        Client client = clientService.findById(source.getClientId());
        result.setClient(client);
        result.setIsImportant(source.getIsImportant());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate deliveryDate = LocalDate.parse(source.getDeliveryDate(), formatter);
        result.setDeliveryDate(deliveryDate);
        return result;
    }
    //todo badRequest exception
}