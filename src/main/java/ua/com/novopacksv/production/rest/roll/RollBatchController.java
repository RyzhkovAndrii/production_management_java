package ua.com.novopacksv.production.rest.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.roll.RollBatchResponse;
import ua.com.novopacksv.production.model.rollModel.RollBatch;
import ua.com.novopacksv.production.service.roll.RollBatchService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/roll-batches", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class RollBatchController {

    private final RollBatchService rollBatchService;

    private final ModelConversionService conversionService;

    @GetMapping
    public ResponseEntity<List<RollBatchResponse>> findAllByManufacturedDate(
            @RequestParam("date") LocalDate date) {
        List<RollBatch> rollBatches =
                rollBatchService.findAllByManufacturedDate(date);
        List<RollBatchResponse> response = conversionService.convert(rollBatches, RollBatchResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RollBatchResponse>> findAllByRollTypeIdAndManufacturedPeriod(
            @RequestParam("roll_type_id") Long rollTypeId,
            @RequestParam("from") LocalDate fromDate,
            @RequestParam("to") LocalDate toDate) {
        List<RollBatch> rollBatches =
                rollBatchService.findAllByRollTypeIdAndManufacturedPeriod(rollTypeId, fromDate, toDate);
        List<RollBatchResponse> response = conversionService.convert(rollBatches, RollBatchResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RollBatchResponse>> findAllByManufacturedPeriod(
            @RequestParam("from") LocalDate fromDate,
            @RequestParam("to") LocalDate toDate) {
        List<RollBatch> rollBatches =
                rollBatchService.findAllByManufacturedPeriod(fromDate, toDate);
        List<RollBatchResponse> response = conversionService.convert(rollBatches, RollBatchResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<RollBatchResponse> findByRollTypeIdAndManufacturedDate(
            @RequestParam("roll_type_id") Long rollTypeId,
            @RequestParam("date") LocalDate date) {
        RollBatch rollBatch = rollBatchService.findByRollTypeIdAndManufacturedDate(rollTypeId, date);
        RollBatchResponse response = conversionService.convert(rollBatch, RollBatchResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}