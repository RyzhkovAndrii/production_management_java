package ua.com.novopacksv.production.rest.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.roll.RollOperationRequest;
import ua.com.novopacksv.production.dto.roll.RollOperationResponse;
import ua.com.novopacksv.production.model.rollModel.RollOperation;
import ua.com.novopacksv.production.service.roll.RollOperationService;

import java.util.List;

@RestController
@RequestMapping(value = "/roll-operations", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class RollOperationController {

    private final RollOperationService rollOperationService;

    private final ModelConversionService conversionService;

    @GetMapping
    public ResponseEntity<List<RollOperationResponse>> getList() {
        List<RollOperation> rollOperations = rollOperationService.findAll();
        List<RollOperationResponse> response = conversionService.convert(rollOperations, RollOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RollOperationResponse> getById(@PathVariable Long id) {
        RollOperation rollOperation = rollOperationService.findById(id);
        RollOperationResponse response = conversionService.convert(rollOperation, RollOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RollOperationResponse> save(@RequestBody RollOperationRequest request) {
        RollOperation rollOperation = conversionService.convert(request, RollOperation.class);
        rollOperation = rollOperationService.save(rollOperation);
        RollOperationResponse response = conversionService.convert(rollOperation, RollOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RollOperationResponse> update(@PathVariable Long id,
                                                        @RequestBody RollOperationRequest request) {
        RollOperation rollOperation = conversionService.convert(request, RollOperation.class);
        rollOperation.setId(id);
        rollOperation = rollOperationService.update(rollOperation);
        RollOperationResponse response = conversionService.convert(rollOperation, RollOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        rollOperationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
