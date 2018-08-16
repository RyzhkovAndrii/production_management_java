package ua.com.novopacksv.production.rest.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.plan.MachinePlanRequest;
import ua.com.novopacksv.production.dto.plan.MachinePlanResponse;
import ua.com.novopacksv.production.model.planModel.MachinePlan;
import ua.com.novopacksv.production.service.plan.MachinePlanService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "machine-plans", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_TECHNOLOGIST', 'ROLE_CMO', 'ROLE_CTO','ROLE_ECONOMIST')")
public class MachinePlanController {

    private final MachinePlanService machinePlanService;

    private final ModelConversionService conversionService;

    @GetMapping(params = {"machine_number", "date"})
    public ResponseEntity<List<MachinePlanResponse>> getAll(@RequestParam("machine_number") Integer machineNumber,
                                                            @RequestParam("date") LocalDate date,
                                                            @RequestParam(name = "sort", defaultValue = "id") String sort) {
        List<MachinePlan> plans = machinePlanService.findSort(machineNumber, date, sort);
        List<MachinePlanResponse> responses = conversionService.convert(plans, MachinePlanResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MachinePlanResponse> getOne(@PathVariable Long id) {
        MachinePlan machinePlan = machinePlanService.findById(id);
        MachinePlanResponse response = conversionService.convert(machinePlan, MachinePlanResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MachinePlanResponse>> getAll() {
        List<MachinePlan> machinePlans = machinePlanService.findAll();
        List<MachinePlanResponse> responses = conversionService.convert(machinePlans, MachinePlanResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_CMO', 'ROLE_CTO')")
    public ResponseEntity<MachinePlanResponse> save(@RequestBody MachinePlanRequest request) {
        MachinePlan machinePlan = conversionService.convert(request, MachinePlan.class);
        machinePlanService.save(machinePlan);
        MachinePlanResponse response = conversionService.convert(machinePlan, MachinePlanResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CMO', 'ROLE_CTO')")
    public ResponseEntity<MachinePlanResponse> update(@PathVariable Long id,
                                                      @RequestBody MachinePlanRequest request) {
        MachinePlan machinePlan = conversionService.convert(request, MachinePlan.class);
        machinePlan.setId(id);
        machinePlanService.update(machinePlan);
        MachinePlanResponse response = conversionService.convert(machinePlan, MachinePlanResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CMO', 'ROLE_CTO')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        machinePlanService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
