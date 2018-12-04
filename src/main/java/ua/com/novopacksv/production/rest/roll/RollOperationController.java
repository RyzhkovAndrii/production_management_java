package ua.com.novopacksv.production.rest.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.roll.RollOperationRequest;
import ua.com.novopacksv.production.dto.roll.RollOperationResponse;
import ua.com.novopacksv.production.dto.roll.RollOperationResponseWithProduct;
import ua.com.novopacksv.production.model.rollModel.RollOperation;
import ua.com.novopacksv.production.service.roll.RollOperationService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "${spring.rest.api-url-prefix}/roll-operations", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@PreAuthorize("hasAnyRole('ROLE_TECHNOLOGIST', 'ROLE_CMO', 'ROLE_CTO'," +
        " 'ROLE_ACCOUNTANT', 'ROLE_ECONOMIST', 'ROLE_STOREKEEPER', 'ROLE_FULL_ACCESS')")
@RequiredArgsConstructor
public class RollOperationController {

    private final RollOperationService rollOperationService;

    private final ModelConversionService conversionService;

    @GetMapping
    public ResponseEntity<List<RollOperationResponse>> getAll() {
        List<RollOperation> rollOperations = rollOperationService.findAll();
        List<RollOperationResponse> response = conversionService.convert(rollOperations, RollOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"roll_type_id"})
    public ResponseEntity<List<RollOperationResponse>> getAll(@RequestParam("roll_type_id") Long rollTypeId) {
        List<RollOperation> rollOperations = rollOperationService.findAllByRollTypeId(rollTypeId);
        List<RollOperationResponse> response = conversionService.convert(rollOperations, RollOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"roll_type_id", "from", "to"})
    public ResponseEntity<List<RollOperationResponse>> getAllByRollTypeIdAndOperationPeriod(
            @RequestParam("roll_type_id") Long rollTypeId,
            @RequestParam("from") LocalDate from,
            @RequestParam("to") LocalDate to) {
        List<RollOperation> rollOperations =
                rollOperationService.findAllByRollTypeIdAndOperationPeriod(rollTypeId, from, to);
        List<RollOperationResponse> response = conversionService.convert(rollOperations, RollOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"roll_type_id", "from_manuf", "to_manuf"})
    public ResponseEntity<List<RollOperationResponseWithProduct>> getAllByRollTypeIdAndManufacturedPeriod(
            @RequestParam("roll_type_id") Long rollTypeId,
            @RequestParam("from_manuf") LocalDate from,
            @RequestParam("to_manuf") LocalDate to) {
        List<RollOperation> rollOperations = rollOperationService
                .findAllByRollTypeIdAndManufacturedPeriod(rollTypeId, from, to);
        List<RollOperationResponseWithProduct> response = conversionService.convert(rollOperations, RollOperationResponseWithProduct.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RollOperationResponse> getOne(@PathVariable Long id) {
        RollOperation rollOperation = rollOperationService.findById(id);
        RollOperationResponse response = conversionService.convert(rollOperation, RollOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_TECHNOLOGIST', 'ROLE_CTO', 'ROLE_FULL_ACCESS')")
    public ResponseEntity<RollOperationResponse> save(@Valid @RequestBody RollOperationRequest request) {
        RollOperation rollOperation = conversionService.convert(request, RollOperation.class);
        rollOperation = rollOperationService.save(rollOperation);
        RollOperationResponse response = conversionService.convert(rollOperation, RollOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_TECHNOLOGIST', 'ROLE_CTO', 'ROLE_FULL_ACCESS')")
    public ResponseEntity<RollOperationResponse> update(@PathVariable Long id, @Valid @RequestBody RollOperationRequest request) {
        RollOperation rollOperation = conversionService.convert(request, RollOperation.class);
        rollOperation.setId(id);
        rollOperation = rollOperationService.update(rollOperation);
        RollOperationResponse response = conversionService.convert(rollOperation, RollOperationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_TECHNOLOGIST', 'ROLE_CTO', 'ROLE_FULL_ACCESS')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rollOperationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}