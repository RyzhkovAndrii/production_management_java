package ua.com.novopacksv.production.rest.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.plan.ProductPlanOperationRequest;
import ua.com.novopacksv.production.dto.plan.ProductPlanOperationResponse;
import ua.com.novopacksv.production.model.planModel.ProductPlanOperation;
import ua.com.novopacksv.production.service.plan.ProductPlanOperationService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "product-plan-operations", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class ProductPlanOperationController {

    private final ProductPlanOperationService productPlanOperationService;

    private final ModelConversionService conversionService;

    @GetMapping(params = {"id", "from", "to"})
    public ResponseEntity<List<ProductPlanOperationResponse>> getAllByProduct(@RequestParam("id") Long productTypeId,
                                                                              @RequestParam("from") LocalDate fromDate,
                                                                              @RequestParam("to") LocalDate toDate) {
        List<ProductPlanOperation> productPlanOperations = productPlanOperationService.getAll(productTypeId, fromDate,
                toDate);
        List<ProductPlanOperationResponse> response = conversionService.convert(productPlanOperations,
                ProductPlanOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"roll_id", "from", "to"})
    public ResponseEntity<List<ProductPlanOperationResponse>> getAllByRoll(@RequestParam("roll_id") Long rollTypeId,
                                                                           @RequestParam("from") LocalDate fromDate,
                                                                           @RequestParam("to") LocalDate toDate) {
        List<ProductPlanOperation> productPlanOperations = productPlanOperationService.getAllByRollTypeId(rollTypeId,
                fromDate, toDate);
        List<ProductPlanOperationResponse> responses = conversionService.convert(productPlanOperations,
                ProductPlanOperationResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductPlanOperationResponse>> getAll() {
        List<ProductPlanOperation> productPlanOperations = productPlanOperationService.findAll();
        List<ProductPlanOperationResponse> responses = conversionService.convert(productPlanOperations,
                ProductPlanOperationResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductPlanOperationResponse> getOne(@PathVariable Long id) {
        ProductPlanOperation productPlanOperation = productPlanOperationService.findById(id);
        ProductPlanOperationResponse response = conversionService.convert(productPlanOperation,
                ProductPlanOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductPlanOperationResponse> save(@RequestBody ProductPlanOperationRequest request) {
        ProductPlanOperation productPlanOperation = conversionService.convert(request, ProductPlanOperation.class);
        ProductPlanOperationResponse response = conversionService.convert(productPlanOperation,
                ProductPlanOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductPlanOperationResponse> update(@PathVariable Long id,
                                                               @RequestBody ProductPlanOperationRequest request) {
        ProductPlanOperation productPlanOperation = conversionService.convert(request, ProductPlanOperation.class);
        productPlanOperation.setId(id);
        ProductPlanOperationResponse response = conversionService.convert(productPlanOperation,
                ProductPlanOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productPlanOperationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
