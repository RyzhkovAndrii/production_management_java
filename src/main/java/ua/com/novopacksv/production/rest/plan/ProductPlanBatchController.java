package ua.com.novopacksv.production.rest.plan;

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
import ua.com.novopacksv.production.dto.plan.ProductPlanBatchResponse;
import ua.com.novopacksv.production.model.planModel.ProductPlanBatch;
import ua.com.novopacksv.production.service.plan.ProductPlanBatchService;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "product-plan-batches", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_TECHNOLOGIST', 'ROLE_CMO', 'ROLE_CTO','ROLE_ECONOMIST', 'ROLE_MANAGER')")
public class ProductPlanBatchController {

    private final ProductPlanBatchService productPlanBatchService;

    private final ModelConversionService conversionService;

    @GetMapping(params = {"id", "date"})
    public ResponseEntity<ProductPlanBatchResponse> getOne(@RequestParam("id") Long productTypeId,
                                                           @RequestParam("date") LocalDate date) {
        ProductPlanBatch productPlanBatch = productPlanBatchService.getOne(productTypeId, date);
        ProductPlanBatchResponse response = conversionService.convert(productPlanBatch, ProductPlanBatchResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"date"})
    public ResponseEntity<List<ProductPlanBatchResponse>> getAll(@RequestParam("date") LocalDate date) {
        List<ProductPlanBatch> productPlanBatches = productPlanBatchService.getAll(date);
        List<ProductPlanBatchResponse> responses = conversionService.convert(productPlanBatches,
                ProductPlanBatchResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }


    @GetMapping(params = {"fromDate", "toDate"})
    @SuppressWarnings("Convert2MethodRef")
    public ResponseEntity<Map<Long, List<ProductPlanBatchResponse>>> getAll(@RequestParam("fromDate") LocalDate fromDate,
                                                                            @RequestParam("toDate") LocalDate toDate) {
        Map<Long, List<ProductPlanBatch>> productPlanBatches = productPlanBatchService.getAll(fromDate, toDate);
        Map<Long, List<ProductPlanBatchResponse>> response = productPlanBatches.entrySet()
                .stream()
                .map(this::convertEntry)
                .collect(Collectors.toMap(
                        k -> k.getKey(),
                        v -> v.getValue()
                ));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private AbstractMap.SimpleEntry<Long, List<ProductPlanBatchResponse>> convertEntry(Map.Entry<Long,
            List<ProductPlanBatch>> entry) {
        return new AbstractMap.SimpleEntry<>(entry.getKey(),
                conversionService.convert(entry.getValue(), ProductPlanBatchResponse.class));
    }
}
