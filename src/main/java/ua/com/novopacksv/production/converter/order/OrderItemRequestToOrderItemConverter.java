package ua.com.novopacksv.production.converter.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.order.OrderItemRequest;
import ua.com.novopacksv.production.model.orderModel.Order;
import ua.com.novopacksv.production.model.orderModel.OrderItem;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.service.order.OrderService;
import ua.com.novopacksv.production.service.product.ProductTypeService;

@Component
public class OrderItemRequestToOrderItemConverter implements Converter<OrderItemRequest, OrderItem> {

    @Autowired
    @Lazy
    private ProductTypeService productTypeService;

    @Autowired
    @Lazy
    private OrderService orderService;

    @Override
    public OrderItem convert(OrderItemRequest source) {
        ProductType productType = productTypeService.findById(source.getProductTypeId());
        Order order = orderService.findById(source.getOrderId());
        OrderItem result = new OrderItem();
        result.setProduct(productType);
        result.setAmount(source.getAmount());
        result.setOrder(order);
        return result;
    }

}