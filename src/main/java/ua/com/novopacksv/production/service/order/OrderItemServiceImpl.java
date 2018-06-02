package ua.com.novopacksv.production.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.orderModel.Order;
import ua.com.novopacksv.production.model.orderModel.OrderItem;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.repository.orderRepository.OrderItemRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    private final OrderService orderService;

    @Override
    @Transactional(readOnly = true)
    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Order item whit id = %d is not found!", id);
            return new ResourceNotFoundException(message);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem update(OrderItem orderItem) {
        return save(orderItem);
    }

    @Override
    public void delete(Long id) {
        orderItemRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> findAll(Long orderId) {
        Order order = orderService.findById(orderId);
        return orderItemRepository.findAllByOrder(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> findAll(ProductType productType, LocalDate fromDate, LocalDate toDate) {
        return orderItemRepository.findAllByProductTypeAndOrder_DeliveryDateBetween(productType, fromDate, toDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> findAllNotDelivered(ProductType productType, LocalDate toDate) {
        return orderItemRepository
                .findAllByProductTypeAndOrder_IsDeliveredIsFalseAndOrder_DeliveryDateBefore(productType, toDate);
    }
}