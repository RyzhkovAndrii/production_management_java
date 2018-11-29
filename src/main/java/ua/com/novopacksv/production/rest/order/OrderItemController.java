package ua.com.novopacksv.production.rest.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.order.OrderItemRequest;
import ua.com.novopacksv.production.dto.order.OrderItemResponse;
import ua.com.novopacksv.production.model.orderModel.OrderItem;
import ua.com.novopacksv.production.service.order.OrderItemService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "${spring.rest.api-url-prefix}/order-items", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_CMO', 'ROLE_ECONOMIST', 'ROLE_FULL_ACCESS')")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    private final ModelConversionService conversionService;

    @GetMapping
    public ResponseEntity<List<OrderItemResponse>> getAll() {
        List<OrderItem> orderItems = orderItemService.findAll();
        List<OrderItemResponse> response = conversionService.convert(orderItems, OrderItemResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponse> getOne(@PathVariable Long id) {
        OrderItem orderItem = orderItemService.findById(id);
        OrderItemResponse response = conversionService.convert(orderItem, OrderItemResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_FULL_ACCESS')")
    public ResponseEntity<OrderItemResponse> save(@Valid @RequestBody OrderItemRequest request) {
        OrderItem orderItem = conversionService.convert(request, OrderItem.class);
        orderItem = orderItemService.save(orderItem);
        OrderItemResponse response = conversionService.convert(orderItem, OrderItemResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_FULL_ACCESS')")
    public ResponseEntity<OrderItemResponse> update(@PathVariable Long id, @Valid @RequestBody OrderItemRequest request) {
        OrderItem orderItem = conversionService.convert(request, OrderItem.class);
        orderItem.setId(id);
        orderItem = orderItemService.update(orderItem);
        OrderItemResponse response = conversionService.convert(orderItem, OrderItemResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_FULL_ACCESS')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        orderItemService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}