package ua.com.novopacksv.production.rest.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.product.ProductTypeRequest;
import ua.com.novopacksv.production.dto.product.ProductTypeResponse;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.service.product.ProductTypeService;

import java.util.List;

@RestController
@RequestMapping(value = "/product-types", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@PreAuthorize("hasAnyRole('ROLE_TECHNOLOGIST', 'ROLE_MANAGER', 'ROLE_CMO', 'ROLE_CTO'," +
        " 'ROLE_ACOUNTER', 'ROLE_ECONOMIST', 'ROLE_STOREKEEPER')")
@RequiredArgsConstructor
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    private final ModelConversionService conversionService;

    @GetMapping
    public ResponseEntity<List<ProductTypeResponse>> getList() {
        List<ProductType> productTypes = productTypeService.findAll();
        List<ProductTypeResponse> response = conversionService.convert(productTypes, ProductTypeResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"name"})
    public ResponseEntity<List<ProductTypeResponse>> getList(@RequestParam("name") String name) {
        List<ProductType> productTypes = productTypeService.findAll(name);
        List<ProductTypeResponse> responses = conversionService.convert(productTypes, ProductTypeResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductTypeResponse> getById(@PathVariable Long id) {
        ProductType productType = productTypeService.findById(id);
        ProductTypeResponse response = conversionService.convert(productType, ProductTypeResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_TECHNOLOGIST', 'ROLE_MANAGER', 'ROLE_CTO')")
    public ResponseEntity<ProductTypeResponse> save(@RequestBody ProductTypeRequest request) {
        ProductType productType = conversionService.convert(request, ProductType.class);
        productType = productTypeService.save(productType);
        ProductTypeResponse response = conversionService.convert(productType, ProductTypeResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_TECHNOLOGIST', 'ROLE_MANAGER', 'ROLE_CTO')")
    public ResponseEntity<ProductTypeResponse> update(@PathVariable Long id, @RequestBody ProductTypeRequest request) {
        ProductType productType = conversionService.convert(request, ProductType.class);
        productType.setId(id);
        productType = productTypeService.update(productType);
        ProductTypeResponse response = conversionService.convert(productType, ProductTypeResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_TECHNOLOGIST', 'ROLE_MANAGER', 'ROLE_CTO')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        productTypeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}