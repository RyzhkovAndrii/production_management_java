package ua.com.novopacksv.production.rest.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.novopacksv.production.service.order.OrderItemService;
import ua.com.novopacksv.production.converter.ModelConversionService;

@RestController
@RequestMapping(value = "/order-items", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    private final ModelConversionService conversionService;

}
