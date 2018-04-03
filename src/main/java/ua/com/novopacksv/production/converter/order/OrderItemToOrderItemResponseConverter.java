package ua.com.novopacksv.production.converter.order;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.order.OrderItemResponse;
import ua.com.novopacksv.production.model.orderModel.OrderItem;

@Component
public class OrderItemToOrderItemResponseConverter implements Converter<OrderItem, OrderItemResponse> {

    @Override
    public OrderItemResponse convert(OrderItem source) {
        OrderItemResponse result = new OrderItemResponse();
        result.setId(source.getId());
        result.setProductId(source.getProduct().getId());
        result.setAmount(source.getAmount());
        result.setOrderId(source.getOrder().getId());
        return result;
    }

}