package ua.com.novopacksv.production.rest.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.order.OrderItemResponse;
import ua.com.novopacksv.production.dto.order.OrderRequest;
import ua.com.novopacksv.production.dto.order.OrderResponse;
import ua.com.novopacksv.production.model.orderModel.Order;
import ua.com.novopacksv.production.model.orderModel.OrderItem;
import ua.com.novopacksv.production.service.order.OrderService;

import java.util.List;

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final ModelConversionService conversionService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getList() {
        List<Order> clients = orderService.findAll();
        List<OrderResponse> response = conversionService.convert(clients, OrderResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        Order order = orderService.findById(id);
        OrderResponse response = conversionService.convert(order, OrderResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> save(@RequestBody OrderRequest request) {
        Order order = conversionService.convert(request, Order.class);
        order = orderService.save(order);
        OrderResponse response = conversionService.convert(order, OrderResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable Long id, @RequestBody OrderRequest request) {
        Order order = conversionService.convert(request, Order.class);
        order.setId(id);
        order = orderService.update(order);
        OrderResponse response = conversionService.convert(order, OrderResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/order-items/")
    public ResponseEntity<List<OrderItemResponse>> getOrderItemsByOrderId(@PathVariable Long id) {
        Order order = orderService.findById(id);
        List<OrderItem> orderItems = order.getOrderItems();
        List<OrderItemResponse> response = conversionService.convert(orderItems, OrderItemResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/order-items/{orderItemId}/")
    public ResponseEntity<Void> addOrderItemToOrder(@PathVariable("orderId") Long orderId,
                                                    @PathVariable("orderItemId") Long orderItemId) {
        orderService.addOrderItemToOrder(orderId, orderItemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}/order-items/{orderItemId}/")
    public ResponseEntity<Void> removeOrderItemFromOrder(@PathVariable("orderId") Long orderId,
                                                         @PathVariable("orderItemId") Long orderItemId) {
        orderService.removeOrderItemFromOrder(orderId, orderItemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}