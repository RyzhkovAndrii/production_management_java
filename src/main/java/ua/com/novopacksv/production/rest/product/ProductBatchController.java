package ua.com.novopacksv.production.rest.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.product.ProductBatchResponse;
import ua.com.novopacksv.production.model.productModel.ProductBatch;
import ua.com.novopacksv.production.service.product.ProductBatchService;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/product-batches", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class ProductBatchController {

    private final ProductBatchService productBatchService;

    private final ModelConversionService conversionService;

    @GetMapping(params = {"product-type-id", "from", "to"})
    public ResponseEntity<ProductBatchResponse> getOne(@RequestParam("product-type-id") Long productTypeId,
                                                       @RequestParam("from") LocalDate fromDate,
                                                       @RequestParam("to") LocalDate toDate) {
        ProductBatch productBatch = productBatchService.getOne(productTypeId, fromDate, toDate);
        ProductBatchResponse response = conversionService.convert(productBatch, ProductBatchResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"product-type-id", "date"})
    public ResponseEntity<ProductBatchResponse> getOne(@RequestParam("product-type-id") Long productTypeId,
                                                       @RequestParam("date") LocalDate date) {
        ProductBatch productBatch = productBatchService.getOne(productTypeId, date);
        ProductBatchResponse response = conversionService.convert(productBatch, ProductBatchResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
