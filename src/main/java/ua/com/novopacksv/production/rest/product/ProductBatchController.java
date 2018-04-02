package ua.com.novopacksv.production.rest.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.novopacksv.production.service.product.ProductBatchService;
import ua.com.novopacksv.production.converter.ModelConversionService;

@RestController
@RequestMapping(value = "/product-batches", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class ProductBatchController {

    private final ProductBatchService productBatchService;

    private final ModelConversionService conversionService;

}
