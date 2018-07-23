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
import ua.com.novopacksv.production.dto.product.ProductLeftOverResponse;
import ua.com.novopacksv.production.model.productModel.ProductLeftOver;
import ua.com.novopacksv.production.service.product.ProductLeftOverService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/product-leftovers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_TECHNOLOGIST', 'ROLE_MANAGER', 'ROLE_CMO', 'ROLE_CTO'," +
        " 'ROLE_ACOUNTER', 'ROLE_ECONOMIST', 'ROLE_STOREKEEPER')")
public class ProductLeftOverController {

    private final ProductLeftOverService productLeftOverService;

    private final ModelConversionService conversionService;

    @GetMapping(params = {"product-type-id", "date"})
    public ResponseEntity<ProductLeftOverResponse> getOne(@RequestParam("product-type-id") Long productTypeId,
                                                          @RequestParam("date")LocalDate date){
        ProductLeftOver productLeftOver = productLeftOverService.findByProductType_IdOnDate(productTypeId, date);
        ProductLeftOverResponse response = conversionService.convert(productLeftOver, ProductLeftOverResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"date"})
    public ResponseEntity<List<ProductLeftOverResponse>> getAll(@RequestParam ("date") LocalDate date){
        List<ProductLeftOver> productLeftOvers = productLeftOverService.findOnDate(date);
        List<ProductLeftOverResponse> responses =
                conversionService.convert(productLeftOvers, ProductLeftOverResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping(params = {"latest"})
    public ResponseEntity<List<ProductLeftOverResponse>> getAll(){
        List<ProductLeftOver> productLeftOvers = productLeftOverService.findLatest();
        List<ProductLeftOverResponse> responses =
                conversionService.convert(productLeftOvers, ProductLeftOverResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

}