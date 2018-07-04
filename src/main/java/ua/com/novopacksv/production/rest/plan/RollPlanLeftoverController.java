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
import ua.com.novopacksv.production.dto.roll.RollLeftOverResponse;
import ua.com.novopacksv.production.model.rollModel.RollLeftOver;
import ua.com.novopacksv.production.service.plan.RollPlanLeftoverService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "roll-plan-leftover", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class RollPlanLeftoverController {

    private final RollPlanLeftoverService rollPlanLeftoverService;

    @Autowired
    @Lazy
    private ModelConversionService conversionService;

    @GetMapping(params = {"id", "from", "to"})
    public ResponseEntity<RollLeftOverResponse> getOneWithoutPlan(@RequestParam ("id") Long rollTypeId,
                                                                  @RequestParam ("from")LocalDate fromDate,
                                                                  @RequestParam ("to") LocalDate toDate){
        RollLeftOver rollLeftOver = rollPlanLeftoverService.getOneWithoutPlan(rollTypeId, fromDate, toDate);
        RollLeftOverResponse response = conversionService.convert(rollLeftOver, RollLeftOverResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"id", "from", "to"})
    public ResponseEntity<RollLeftOverResponse> getOneTotal(@RequestParam ("id") Long rollTypeId,
                                                            @RequestParam ("from")LocalDate fromDate,
                                                            @RequestParam ("to") LocalDate toDate){
        RollLeftOver rollLeftOver = rollPlanLeftoverService.getOneTotal(rollTypeId, fromDate, toDate);
        RollLeftOverResponse response = conversionService.convert(rollLeftOver, RollLeftOverResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"from", "to"})
    public ResponseEntity<List<RollLeftOverResponse>> getAllWithoutPlan(@RequestParam ("from")LocalDate fromDate,
                                                                @RequestParam ("to") LocalDate toDate){
        List<RollLeftOver> rollLeftOvers = rollPlanLeftoverService.getAllWithoutPlan(fromDate, toDate);
        List<RollLeftOverResponse> responses = conversionService.convert(rollLeftOvers, RollLeftOverResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping(params = {"from", "to"})
    public ResponseEntity<List<RollLeftOverResponse>> getAllTotal(@RequestParam ("from")LocalDate fromDate,
                                                                        @RequestParam ("to") LocalDate toDate){
        List<RollLeftOver> rollLeftOvers = rollPlanLeftoverService.getAllTotal(fromDate, toDate);
        List<RollLeftOverResponse> responses = conversionService.convert(rollLeftOvers, RollLeftOverResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
