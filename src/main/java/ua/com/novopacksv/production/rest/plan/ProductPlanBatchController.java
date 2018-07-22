package ua.com.novopacksv.production.rest.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.plan.ProductPlanBatchResponse;
import ua.com.novopacksv.production.model.planModel.ProductPlanBatch;
import ua.com.novopacksv.production.service.plan.ProductPlanBatchService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "product-plan-batches", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class ProductPlanBatchController {

    private final ProductPlanBatchService productPlanBatchService;

    private final ModelConversionService conversionService;

    @GetMapping(params = {"id", "date"})
    public ResponseEntity<ProductPlanBatchResponse> getOne(@RequestParam ("id") Long productTypeId,
                                                           @RequestParam ("date")LocalDate date){
        ProductPlanBatch productPlanBatch = productPlanBatchService.getOne(productTypeId, date);
        ProductPlanBatchResponse response = conversionService.convert(productPlanBatch, ProductPlanBatchResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"date"})
    public ResponseEntity<List<ProductPlanBatchResponse>> getAll(@RequestParam ("date") LocalDate date){
        List<ProductPlanBatch> productPlanBatches = productPlanBatchService.getAll(date);
        List<ProductPlanBatchResponse> responses = conversionService.convert(productPlanBatches,
                ProductPlanBatchResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
