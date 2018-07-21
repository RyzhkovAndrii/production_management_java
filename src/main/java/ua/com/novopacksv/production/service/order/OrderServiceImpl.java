package ua.com.novopacksv.production.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.orderModel.Order;
import ua.com.novopacksv.production.model.userModel.TableType;
import ua.com.novopacksv.production.repository.orderRepository.OrderRepository;
import ua.com.novopacksv.production.service.user.TableModificationService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final static TableType TABLE_TYPE_FOR_UPDATE = TableType.ORDERS;

    private final OrderRepository orderRepository;

    private final TableModificationService tableModificationService;

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
        tableModificationService.update(TABLE_TYPE_FOR_UPDATE);
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        return save(order);
    }

    @Override
    public void delete(Long id) {
        tableModificationService.update(TABLE_TYPE_FOR_UPDATE);
        orderRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll(LocalDate fromDeliveryDate, LocalDate toDeliveryDate) {
        return orderRepository.findAllByDeliveryDateBetween(fromDeliveryDate, toDeliveryDate);
    }

    @Override
    @Transactional(readOnly = true)
    public LocalDate findMaxDeliveryDate() {
        return orderRepository.findFirstByOrderByDeliveryDateDesc().getDeliveryDate();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAllNotDelivered(String sortProperties) {
        Sort sort = new Sort(Sort.Direction.ASC, sortProperties);
        return orderRepository.findAllByActualDeliveryDateIsNull(sort);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAllDelivered(LocalDate from, String sortProperties) {
        Sort sort = new Sort(Sort.Direction.ASC, sortProperties);
        return from == null
                ? orderRepository.findAllByActualDeliveryDateIsNotNull(sort)
                : orderRepository.findAllByActualDeliveryDateAfter(from, sort);
    }
}