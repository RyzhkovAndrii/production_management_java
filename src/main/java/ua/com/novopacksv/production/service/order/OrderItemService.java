package ua.com.novopacksv.production.service.order;

import ua.com.novopacksv.production.model.orderModel.OrderItem;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.service.BaseEntityService;

import java.time.LocalDate;
import java.util.List;

public interface OrderItemService extends BaseEntityService<OrderItem> {

    List<OrderItem> findAll(Long orderId);

    List<OrderItem> findAll(ProductType productType, LocalDate fromDate, LocalDate toDate);

    List<OrderItem> findAllNotDelivered(ProductType productType, LocalDate toDate);

}
