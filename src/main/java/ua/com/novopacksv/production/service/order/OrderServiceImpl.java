package ua.com.novopacksv.production.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.orderModel.Order;
import ua.com.novopacksv.production.model.orderModel.OrderItem;
import ua.com.novopacksv.production.repository.orderRepository.OrderRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemService orderItemService;

    @Override
    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Order whit id = %d is not found!", id);
            return new ResourceNotFoundException(message);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        return save(order);
    }

    @Override
    public void delete(Long id) {
        orderRepository.delete(findById(id));
    }

    @Override
    public void addOrderItemToOrder(Long orderId, Long orderItemId) {
        Order order = findById(orderId);
        OrderItem orderItem = orderItemService.findById(orderItemId);
        order.getOrderItems().add(orderItem);
        orderItem.setOrder(order);
    }

    @Override
    public void removeOrderItemFromOrder(Long orderId, Long orderItemId) {
        Order order = findById(orderId);
        OrderItem orderItem = orderItemService.findById(orderItemId);
        order.getOrderItems().remove(orderItem);
        orderItem.setOrder(null);
    }

}