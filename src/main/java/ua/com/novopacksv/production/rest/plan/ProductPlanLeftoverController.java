package ua.com.novopacksv.production.rest.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.product.ProductLeftOverResponse;
import ua.com.novopacksv.production.model.productModel.ProductLeftOver;
import ua.com.novopacksv.production.service.plan.ProductPlanLeftoverService;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "product-plan-leftover", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class ProductPlanLeftoverController {

    private final ProductPlanLeftoverService productPlanLeftoverService;

    @Autowired
    @Lazy
    private ModelConversionService conversionService;

    @GetMapping(params = {"id", "from", "to"})
    public ResponseEntity<ProductLeftOverResponse> getOneWithoutPlan(@RequestParam ("id") Long productTypeId,
                                                                     @RequestParam ("from")LocalDate fromDate,
                                                                     @RequestParam ("to") LocalDate toDate){
        ProductLeftOver productLeftOver = productPlanLeftoverService.getOneWithoutPlan(productTypeId, fromDate, toDate);
        ProductLeftOverResponse response = conversionService.convert(productLeftOver, ProductLeftOverResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"id", "from", "to"})
    public ResponseEntity<ProductLeftOverResponse> getOneTotal(@RequestParam ("id") Long productTypeId,
                                                               @RequestParam ("from")LocalDate fromDate,
                                                               @RequestParam ("to") LocalDate toDate){
        ProductLeftOver productLeftOver = productPlanLeftoverService.getOneTotal(productTypeId, fromDate, toDate);
        ProductLeftOverResponse response = conversionService.convert(productLeftOver, ProductLeftOverResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"from", "to"})
    public ResponseEntity<List<ProductLeftOverResponse>> getAllWithoutPlan(@RequestParam ("from")LocalDate fromDate,
                                                                           @RequestParam ("to") LocalDate toDate){
        List<ProductLeftOver> productLeftOvers = productPlanLeftoverService.getAllWithoutPlan(fromDate, toDate);
        List<ProductLeftOverResponse> responses = conversionService.convert(productLeftOvers, ProductLeftOverResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping(params = {"from", "to"})
    public ResponseEntity<List<ProductLeftOverResponse>> getAllTotal(@RequestParam ("from")LocalDate fromDate,
                                                                           @RequestParam ("to") LocalDate toDate){
        List<ProductLeftOver> productLeftOvers = productPlanLeftoverService.getAllTotal(fromDate, toDate);
        List<ProductLeftOverResponse> responses = conversionService.convert(productLeftOvers, ProductLeftOverResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
