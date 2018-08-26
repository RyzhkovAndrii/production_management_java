package ua.com.novopacksv.production.service.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.orderModel.Order;
import ua.com.novopacksv.production.model.orderModel.OrderItem;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.repository.orderRepository.OrderItemRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Class implements interface {@link OrderItemService} and contains CRUD and find by parameters methods,
 * works with OrderItems
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderItemServiceImpl implements OrderItemService {

    /**
     * An object of repository's layer for work with OrderItem from db
     */
    private final OrderItemRepository orderItemRepository;

    /**
     * An object of service's layer for work with Orders
     */
    private final OrderService orderService;

    /**
     * Method finds OrderItem by id
     *
     * @param id - OrderItem's id
     * @return OrderItem
     * @throws ResourceNotFoundException if OrderItem with this id does not exist
     */
    @Override
    @Transactional(readOnly = true)
    public OrderItem findById(Long id) throws ResourceNotFoundException {
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() -> {
            log.error("Method findById(Long id): OrderItem with id = {} was not found", id);
            String message = String.format("Order item whit id = %d is not found!", id);
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findById(Long id): OrderItem with id = {} was found: {}", id, orderItem);
        return orderItem;
    }

    /**
     * Method finds all existed OrderItems from db
     *
     * @return all OrderItems
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> findAll() {
        log.debug("Method findAll(): List<OrderItem> is finding");
        return orderItemRepository.findAll();
    }

    /**
     * Method saves new OrderItem in db
     *
     * @param orderItem - new OrderItem
     * @return saved new OrderItem
     */
    @Override
    public OrderItem save(OrderItem orderItem) {
        OrderItem orderItemSaved = orderItemRepository.save(orderItem);
        log.debug("Method save(OrderItem orderItem): OrderItem {} was saved", orderItemSaved);
        return orderItemSaved;
    }

    /**
     * Method calls method save(OrderItem orderItem)
     *
     * @param orderItem - OrderItem for update
     * @return saved OrderItem
     * @throws ResourceNotFoundException if OrderItem with this id does not exist in db
     */
    @Override
    public OrderItem update(OrderItem orderItem) throws ResourceNotFoundException {
        findById(orderItem.getId());
        OrderItem orderItemSaved = save(orderItem);
        log.debug("Method update(OrderItem orderItem): OrderItem was updated: {}", orderItemSaved);
        return orderItemSaved;
    }

    /**
     * Method delete OrderItem by id
     *
     * @param id - OrderItem's id
     * @throws ResourceNotFoundException if OrderItem with this id does not exist in db
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        orderItemRepository.delete(findById(id));
        log.debug("Method delete(Long id): OrderItem with id = {} was deleted", id);
    }

    /**
     * Method finds all OrderItems from pointed Order
     *
     * @param orderId - Order's id
     * @return List of OrderItems for one Order
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> findAll(Long orderId) throws ResourceNotFoundException {
        Order order = orderService.findById(orderId);
        log.debug("Method findAll(Long orderId): List<OrderItem> for Order with id ={} is finding", orderId);
        return orderItemRepository.findAllByOrder(order);
    }

    /**
     * Method finds OrderItems for one ProductType for a period
     *
     * @param productType - pointed ProductType
     * @param fromDate    - a beginning of the period
     * @param toDate      - an end of the period
     * @return List of OrderItems
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> findAll(ProductType productType, LocalDate fromDate, LocalDate toDate) {
        log.debug("Method findAll(ProductType productType, LocalDate fromDate, LocalDate toDate): List<OrderItem> " +
                "for ProductType {} from {} to {} is finding", productType, fromDate, toDate);
        return orderItemRepository.findAllByProductTypeAndOrder_DeliveryDateBetween(productType, fromDate, toDate);
    }

    /**
     * Method finds all OrderItems for not delivered Orders for one ProductType up to date
     *
     * @param productType - ProductType
     * @param toDate      - date
     * @return List of OrderItems
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> findAllNotDelivered(ProductType productType, LocalDate toDate) {
        log.debug("Method findAllNotDelivered(ProductType productType, LocalDate toDate): List<OrderItem> for " +
                "ProductType {} up to date {} for not delivered Orders is finding", productType, toDate);
        return orderItemRepository
                .findAllByProductTypeAndOrder_ActualDeliveryDateIsNullAndOrder_DeliveryDateLessThanEqual(productType, toDate);
    }
}