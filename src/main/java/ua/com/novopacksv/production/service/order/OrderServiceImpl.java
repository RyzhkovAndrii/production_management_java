package ua.com.novopacksv.production.service.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

/**
 * Class implements interface {@link OrderService} and contains CRUD and find-methods with different parameters for
 * work with Orders
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    /**
     * Value of ENUM for set TableType.ORDERS for all acts that connect with this methods
     */
    private final static TableType TABLE_TYPE_FOR_UPDATE = TableType.ORDERS;

    /**
     * An object of repository's layer for work with table 'orders' in db
     */
    private final OrderRepository orderRepository;

    /**
     * An object of service's layer for work with TableModification
     */
    private final TableModificationService tableModificationService;

    /**
     * Method finds one Order by it's id
     *
     * @param id - Order's id
     * @return Order by id
     * @throws ResourceNotFoundException if Order with this id does not exist in db
     */
    @Override
    @Transactional(readOnly = true)
    public Order findById(Long id) throws ResourceNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() -> {
            log.error("Method findById(Long id): Order with id = {} was not found", id);
            String message = String.format("Order whit id = %d is not found!", id);
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findById(Long id): Order with id = {} was found: {}", id, order);
        return order;
    }

    /**
     * Method finds all existed in db Orders
     *
     * @return List of Orders
     */
    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        log.debug("Method findAll(): List<Order> is finding");
        return orderRepository.findAll();
    }

    /**
     * Method updates the notification of user who made modification in the table "orders" and save new Order in db
     *
     * @param order - new Order
     * @return saved new Order
     */
    @Override
    public Order save(Order order) {
        tableModificationService.update(TABLE_TYPE_FOR_UPDATE);
        Order orderSaved = orderRepository.save(order);
        log.debug("Method save(Order order): Order {} was saved", orderSaved);
        return orderSaved;
    }

    /**
     * Method calls save(Order order) for existed Order and updates it
     *
     * @param order - Order for update
     * @return updated Order
     */
    @Override
    public Order update(Order order) {
        Order orderUpdated = save(order);
        log.debug("Method update(Order order): Order {} was updated", orderUpdated);
        return orderUpdated;
    }

    /**
     * Method deletes Order by id
     *
     * @param id - Order's id
     */
    @Override
    public void delete(Long id) {
        tableModificationService.update(TABLE_TYPE_FOR_UPDATE);
        orderRepository.delete(findById(id));
        log.debug("Method delete(Long id): Order with id = {} was deleted", id);
    }

    /**
     * Method finds all Order from period
     *
     * @param fromDeliveryDate - a beginning of the period
     * @param toDeliveryDate   - an end of the period
     * @return List of Orders
     */
    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll(LocalDate fromDeliveryDate, LocalDate toDeliveryDate) {
        log.debug("Method findAll(LocalDate fromDeliveryDate, LocalDate toDeliveryDate): List<Order> from {} to {}" +
                "is finding", fromDeliveryDate, toDeliveryDate);
        return orderRepository.findAllByDeliveryDateBetween(fromDeliveryDate, toDeliveryDate);
    }

    /**
     * Method finds the latest deliveryDate
     *
     * @return latest deliveryDate
     */
    @Override
    @Transactional(readOnly = true)
    public LocalDate findMaxDeliveryDate() {
        LocalDate maxDate = orderRepository.findFirstByOrderByDeliveryDateDesc().getDeliveryDate();
        log.debug("Method findMaxDeliveryDate(): LocalDate of latest deliveryDate for Orders are found: {}", maxDate);
        return maxDate;
    }

    /**
     * Method finds all not delivered Orders and sorts its by sortProperties
     *
     * @param sortProperties - parameter for sort
     * @return sorted List of not delivered Orders
     */
    @Override
    @Transactional(readOnly = true)
    public List<Order> findAllNotDelivered(String sortProperties) {
        Sort sort = new Sort(Sort.Direction.ASC, sortProperties);
        log.debug("Method findAllNotDelivered(String sortProperties): List<Order> is finding and sorting by {}",
                sortProperties);
        return orderRepository.findAllByActualDeliveryDateIsNull(sort);
    }

    /**
     * Method finds delivered Orders from a date and sorts its by sortProperties. If date are not pointed, method finds
     * and sorts all delivered Orders
     *
     * @param from           - date from which method finds Orders
     * @param sortProperties - parameter for sort
     * @return sorted List of delivered Orders
     */
    @Override
    @Transactional(readOnly = true)
    public List<Order> findAllDelivered(LocalDate from, String sortProperties) {
        Sort sort = new Sort(Sort.Direction.ASC, sortProperties);
        log.debug("Method findAllDelivered(LocalDate from, String sortProperties): List<Order> is finding and " +
                "sorting by {}", sortProperties);
        return from == null
                ? orderRepository.findAllByActualDeliveryDateIsNotNull(sort)
                : orderRepository.findAllByActualDeliveryDateAfter(from, sort);
    }
}