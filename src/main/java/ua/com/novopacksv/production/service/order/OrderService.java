package ua.com.novopacksv.production.service.order;

public interface OrderService extends BaseEntityService<Order> {

    void addOrderItemToOrder(Long orderId, Long OrderItemId);

    void removeOrderItemFromOrder(Long orderId, Long OrderItemId);

}