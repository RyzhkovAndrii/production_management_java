package ua.com.novopacksv.production.rest.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.order.OrderRequest;
import ua.com.novopacksv.production.dto.order.OrderResponse;
import ua.com.novopacksv.production.model.orderModel.Order;
import ua.com.novopacksv.production.service.order.OrderService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "${spring.rest.api-url-prefix}/orders", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_CMO', 'ROLE_ECONOMIST', 'ROLE_FULL_ACCESS')")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final ModelConversionService conversionService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll() {
        List<Order> clients = orderService.findAll();
        List<OrderResponse> response = conversionService.convert(clients, OrderResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"isDelivered"})
    public ResponseEntity<List<OrderResponse>> getAll(
            @RequestParam(name = "isDelivered") Boolean isDelivered,
            @RequestParam(name = "from", required = false) LocalDate from,
            @RequestParam(name = "sort", defaultValue = "id") String sort
    ) {
        List<Order> clients = isDelivered
                ? orderService.findAllDelivered(from, sort)
                : orderService.findAllNotDelivered(sort);
        List<OrderResponse> response = conversionService.convert(clients, OrderResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOne(@PathVariable Long id) {
        Order order = orderService.findById(id);
        OrderResponse response = conversionService.convert(order, OrderResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_FULL_ACCESS')")
    public ResponseEntity<OrderResponse> save(@Valid @RequestBody OrderRequest request) {
        Order order = conversionService.convert(request, Order.class);
        order = orderService.save(order);
        OrderResponse response = conversionService.convert(order, OrderResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_FULL_ACCESS')")
    public ResponseEntity<OrderResponse> update(@PathVariable Long id, @Valid @RequestBody OrderRequest request) {
        Order order = conversionService.convert(request, Order.class);
        order.setId(id);
        order = orderService.update(order);
        OrderResponse response = conversionService.convert(order, OrderResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_FULL_ACCESS')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}