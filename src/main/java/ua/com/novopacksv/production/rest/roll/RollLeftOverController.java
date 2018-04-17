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
import ua.com.novopacksv.production.dto.roll.RollLeftOverResponse;
import ua.com.novopacksv.production.model.rollModel.RollLeftOver;
import ua.com.novopacksv.production.service.roll.RollLeftOverService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/roll-leftovers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class RollLeftOverController {

    private final RollLeftOverService rollLeftOverService;

    private final ModelConversionService conversionService;

    @GetMapping(params = {"date"})
    public ResponseEntity<List<RollLeftOverResponse>> getAll(@RequestParam("date") LocalDate date) {
        List<RollLeftOver> rollBatches =
                rollLeftOverService.findAllByDate(date);
        List<RollLeftOverResponse> response = conversionService.convert(rollBatches, RollLeftOverResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"roll_type_id", "date"})
    public ResponseEntity<RollLeftOverResponse> getAll(@RequestParam("roll_type_id") Long rollTypeId,
                                                       @RequestParam("date") LocalDate date) {
        RollLeftOver rollBatch = rollLeftOverService.findByRollTypeIdAndDate(rollTypeId, date);
        RollLeftOverResponse response = conversionService.convert(rollBatch, RollLeftOverResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}