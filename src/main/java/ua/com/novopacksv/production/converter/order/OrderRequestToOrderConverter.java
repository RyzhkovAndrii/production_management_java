package ua.com.novopacksv.production.converter.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
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

    @Autowired
    private ConversionService conversionService;

    @Override
    public Order convert(OrderRequest source) {
        Order result = new Order();
        Client client = clientService.findById(source.getClientId());
        LocalDate deliveryDate = conversionService.convert(source.getDeliveryDate(), LocalDate.class);
        result.setClient(client);
        result.setIsImportant(source.getIsImportant());
        result.setDeliveryDate(deliveryDate);
        return result;
    }
    //todo badRequest exception
}