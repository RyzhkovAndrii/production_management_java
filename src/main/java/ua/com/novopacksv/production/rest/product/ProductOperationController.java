package ua.com.novopacksv.production.rest.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.product.ProductOperationRequest;
import ua.com.novopacksv.production.dto.product.ProductOperationResponse;
import ua.com.novopacksv.production.model.productModel.ProductOperation;
import ua.com.novopacksv.production.service.product.ProductOperationService;

import java.util.List;

@RestController
@RequestMapping(value = "product-operations", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class ProductOperationController {

    private final ProductOperationService productOperationService;

    private final ModelConversionService conversionService;

    @GetMapping
    public ResponseEntity<List<ProductOperationResponse>> getAll(){
        List<ProductOperation> productOperations = productOperationService.findAll();
        List<ProductOperationResponse> response =
                conversionService.convert(productOperations, ProductOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOperationResponse> getOne(@PathVariable Long id){
       ProductOperation productOperation = productOperationService.findById(id);
       ProductOperationResponse response = conversionService.convert(productOperation, ProductOperationResponse.class);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductOperationResponse> save(@RequestBody ProductOperationRequest request){
       ProductOperation productOperation = conversionService.convert(request, ProductOperation.class);
       productOperation = productOperationService.save(productOperation);
       ProductOperationResponse response = conversionService.convert(productOperation, ProductOperationResponse.class);
       return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductOperationResponse> update(@PathVariable Long id,
                                                           @RequestBody ProductOperationRequest request){
        ProductOperation productOperation = conversionService.convert(request, ProductOperation.class);
        productOperation.setId(id);
        productOperation = productOperationService.update(productOperation);
        ProductOperationResponse response = conversionService.convert(productOperation, ProductOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productOperationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}