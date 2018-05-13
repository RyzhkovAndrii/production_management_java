package ua.com.novopacksv.production.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.orderModel.Order;
import ua.com.novopacksv.production.repository.orderRepository.OrderRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

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
    @Transactional(readOnly = true)
    public List<Order> findAll(LocalDate fromDeliveryDate, LocalDate toDeliveryDate) {
        return orderRepository.findAllByDeliveryDateBetween(fromDeliveryDate, toDeliveryDate);
    }

}