package ua.com.novopacksv.production.service.order;

import ua.com.novopacksv.production.model.orderModel.OrderItem;
import ua.com.novopacksv.production.service.BaseEntityService;

import java.util.List;

public interface OrderItemService extends BaseEntityService<OrderItem> {

    List<OrderItem> findAll(Long orderId);

}
