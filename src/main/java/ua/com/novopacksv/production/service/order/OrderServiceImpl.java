package ua.com.novopacksv.production.service.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.orderModel.Order;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Override
    public void addOrderItemToOrder(Long orderId, Long OrderItemId) {

    }

    @Override
    public void removeOrderItemFromOrder(Long orderId, Long OrderItemId) {

    }

    @Override
    public Order findById(Long id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Order save(Order order) {
        return null;
    }

    @Override
    public Order update(Order order) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

}