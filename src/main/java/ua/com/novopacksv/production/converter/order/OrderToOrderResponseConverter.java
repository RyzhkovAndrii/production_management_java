package ua.com.novopacksv.production.converter.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.order.OrderResponse;
import ua.com.novopacksv.production.model.orderModel.Order;

import java.time.LocalDate;

@Component
public class OrderToOrderResponseConverter implements Converter<Order, OrderResponse> {

    @Autowired
    @Lazy
    private ConversionService conversionService;

    @Override
    public OrderResponse convert(Order source) {
        String deliveryDate = conversionService.convert(source.getDeliveryDate(), String.class);
        String creationDate = conversionService.convert(source.getCreationDate(), String.class);
        String actualDeliveryDate = conversionService.convert(source.getActualDeliveryDate(), String.class);
        Boolean isDelivered = source.getActualDeliveryDate() != null;
        Boolean isOverdue = source.getActualDeliveryDate() == null
                ? LocalDate.now().isAfter(source.getDeliveryDate())
                : source.getActualDeliveryDate().isAfter(source.getDeliveryDate());
        OrderResponse result = new OrderResponse();
        result.setId(source.getId());
        result.setClientId(source.getClient().getId());
        result.setCity(source.getCity());
        result.setDeliveryDate(deliveryDate);
        result.setIsImportant(source.getIsImportant());
        result.setIsDelivered(isDelivered);
        result.setActualDeliveryDate(actualDeliveryDate);
        result.setCreationDate(creationDate);
        result.setIsOverdue(isOverdue);
        return result;
    }

}
