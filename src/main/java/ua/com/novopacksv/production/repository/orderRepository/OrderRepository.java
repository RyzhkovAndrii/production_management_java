package ua.com.novopacksv.production.repository.orderRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.orderModel.Order;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository <Order, Long> {
    List<Order> findAllByCreationDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
