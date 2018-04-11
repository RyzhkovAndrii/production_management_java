package ua.com.novopacksv.production.rest.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.order.OrderItemResponse;
import ua.com.novopacksv.production.model.orderModel.Order;
import ua.com.novopacksv.production.model.orderModel.OrderItem;
import ua.com.novopacksv.production.service.order.OrderService;

import java.util.List;

@RestController
@RequestMapping(value = "/orders/{orderId}/order-items", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class OrderItemsOfOrderController {

    private final OrderService orderService;

    private final ModelConversionService conversionService;

    @GetMapping
    public ResponseEntity<List<OrderItemResponse>> getOrderItemsByOrderId(@PathVariable("orderId") Long id) {
        Order order = orderService.findById(id);
        List<OrderItem> orderItems = order.getOrderItems();
        List<OrderItemResponse> response = conversionService.convert(orderItems, OrderItemResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{orderItemId}")
    public ResponseEntity<Void> addOrderItemToOrder(@PathVariable("orderId") Long orderId,
                                                    @PathVariable("orderItemId") Long orderItemId) {
        orderService.addOrderItemToOrder(orderId, orderItemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<Void> removeOrderItemFromOrder(@PathVariable("orderId") Long orderId,
                                                         @PathVariable("orderItemId") Long orderItemId) {
        orderService.removeOrderItemFromOrder(orderId, orderItemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}