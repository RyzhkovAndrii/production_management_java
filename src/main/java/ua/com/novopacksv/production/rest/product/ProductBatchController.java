package ua.com.novopacksv.production.rest.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.product.ProductBatchResponse;
import ua.com.novopacksv.production.model.productModel.ProductBatch;
import ua.com.novopacksv.production.service.product.ProductBatchService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "${spring.rest.api-url-prefix}/product-batches", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@PreAuthorize("hasAnyRole('ROLE_TECHNOLOGIST', 'ROLE_MANAGER', 'ROLE_CMO', 'ROLE_CTO'," +
        " 'ROLE_ACOUNTER', 'ROLE_ECONOMIST', 'ROLE_STOREKEEPER')")
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

    @GetMapping(params = {"from", "to"})
    public ResponseEntity<List<ProductBatchResponse>> getAll(@RequestParam("from") LocalDate fromDate,
                                                             @RequestParam("to") LocalDate toDate) {
        List<ProductBatch> productBatches = productBatchService.getAll(fromDate, toDate);
        List<ProductBatchResponse> responses = conversionService.convert(productBatches, ProductBatchResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping(params = {"date"})
    public ResponseEntity<List<ProductBatchResponse>> getAll(@RequestParam("date") LocalDate date) {
        List<ProductBatch> productBatches = productBatchService.getAll(date);
        List<ProductBatchResponse> responses = conversionService.convert(productBatches, ProductBatchResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

}