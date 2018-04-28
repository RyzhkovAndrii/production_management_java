package ua.com.novopacksv.production.rest.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.novopacksv.production.service.product.ProductLeftOverService;
import ua.com.novopacksv.production.converter.ModelConversionService;

@RestController
@RequestMapping(value = "/product-leftovers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class ProductLeftOverController {

    private final ProductLeftOverService productLeftOverService;

    private final ModelConversionService conversionService;



}
