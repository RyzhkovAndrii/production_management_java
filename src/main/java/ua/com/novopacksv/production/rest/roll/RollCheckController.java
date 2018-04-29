package ua.com.novopacksv.production.rest.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.roll.RollCheckRequest;
import ua.com.novopacksv.production.dto.roll.RollCheckResponse;
import ua.com.novopacksv.production.model.rollModel.RollCheck;
import ua.com.novopacksv.production.service.roll.RollCheckService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/roll-checks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class RollCheckController {

    private final RollCheckService rollCheckService;

    private final ModelConversionService conversionService;

    @GetMapping(params = {"roll_type_id"})
    public ResponseEntity<RollCheckResponse> getOne(@RequestParam("roll_type_id") Long rollTypeId) {
        RollCheck rollCheck = rollCheckService.findOneByRollTypeId(rollTypeId);
        RollCheckResponse response = conversionService.convert(rollCheck, RollCheckResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RollCheckResponse>> getAll() {
        List<RollCheck> rollCheckList = rollCheckService.findAll();
        List<RollCheckResponse> response = conversionService.convert(rollCheckList, RollCheckResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RollCheckResponse> update(@PathVariable Long id, @RequestBody @Valid RollCheckRequest request) {
        RollCheck rollCheck = conversionService.convert(request, RollCheck.class);
        rollCheck.setId(id);
        rollCheck = rollCheckService.update(rollCheck);
        RollCheckResponse response = conversionService.convert(rollCheck, RollCheckResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}