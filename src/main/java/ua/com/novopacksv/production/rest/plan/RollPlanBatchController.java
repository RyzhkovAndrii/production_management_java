package ua.com.novopacksv.production.rest.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.plan.RollPlanBatchResponse;
import ua.com.novopacksv.production.model.planModel.RollPlanBatch;
import ua.com.novopacksv.production.service.plan.RollPlanBatchService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "roll-plan-batches", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_TECHNOLOGIST', 'ROLE_CMO', 'ROLE_CTO','ROLE_ECONOMIST')")
public class RollPlanBatchController {

    private final RollPlanBatchService rollPlanBatchService;

    @Autowired
    @Lazy
    private ModelConversionService conversionService;

    @GetMapping(params = {"id", "date"})
    public ResponseEntity<RollPlanBatchResponse> getOne(@RequestParam ("id") Long rollTypeId,
                                                        @RequestParam ("date")LocalDate date){
        RollPlanBatch rollPlanBatch = rollPlanBatchService.getOne(rollTypeId, date);
        RollPlanBatchResponse response = conversionService.convert(rollPlanBatch, RollPlanBatchResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"date"})
    public ResponseEntity<List<RollPlanBatchResponse>> getAll(@RequestParam ("date") LocalDate date){
        List<RollPlanBatch> rollPlanBatches = rollPlanBatchService.getAll(date);
        List<RollPlanBatchResponse> responses = conversionService.convert(rollPlanBatches, RollPlanBatchResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
