package ua.com.novopacksv.production.converter.order;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.order.OrderResponse;
import ua.com.novopacksv.production.model.orderModel.Order;

import java.time.format.DateTimeFormatter;

@Component
public class OrderToOrderResponseConverter implements Converter<Order, OrderResponse> {

    @Override
    public OrderResponse convert(Order source) {
        OrderResponse result = new OrderResponse();
        result.setClientId(source.getClient().getId());
        result.setIsImportant(source.getIsImportant());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        result.setDeliveryDate(source.getDeliveryDate().format(formatter));
        return result;
    }
}