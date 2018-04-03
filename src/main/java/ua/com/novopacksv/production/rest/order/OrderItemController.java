package ua.com.novopacksv.production.rest.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.order.OrderItemRequest;
import ua.com.novopacksv.production.dto.order.OrderItemResponse;
import ua.com.novopacksv.production.model.orderModel.OrderItem;
import ua.com.novopacksv.production.service.order.OrderItemService;

@RestController
@RequestMapping(value = "/order-items", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    private final ModelConversionService conversionService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponse> getById(@PathVariable Long id) {
        OrderItem orderItem = orderItemService.findById(id);
        OrderItemResponse response = conversionService.convert(orderItem, OrderItemResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderItemResponse> save(@RequestBody OrderItemRequest request) {
        OrderItem orderItem = conversionService.convert(request, OrderItem.class);
        orderItem = orderItemService.save(orderItem);
        OrderItemResponse response = conversionService.convert(orderItem, OrderItemResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody OrderItemRequest request) {
        OrderItem orderItem = conversionService.convert(request, OrderItem.class);
        orderItem.setId(id);
        orderItemService.update(orderItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}