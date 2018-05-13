package ua.com.novopacksv.production.service.order;

import ua.com.novopacksv.production.model.orderModel.Order;
import ua.com.novopacksv.production.service.BaseEntityService;

import java.time.LocalDate;
import java.util.List;

public interface OrderService extends BaseEntityService<Order> {

    List<Order> findAll(LocalDate fromDeliveryDate, LocalDate toDeliveryDate);

}