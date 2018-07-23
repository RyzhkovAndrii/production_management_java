package ua.com.novopacksv.production.rest.norm;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.norm.NormForRollResponse;
import ua.com.novopacksv.production.dto.norm.NormRequest;
import ua.com.novopacksv.production.dto.norm.NormResponse;
import ua.com.novopacksv.production.model.normModel.Norm;
import ua.com.novopacksv.production.service.norm.NormService;

import java.util.List;

@RestController
@RequestMapping(value = "/norms", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@PreAuthorize("hasAnyRole('ROLE_TECHNOLOGIST', 'ROLE_CMO', 'ROLE_CTO', 'ROLE_ECONOMIST')")
@RequiredArgsConstructor
public class NormController {

    private final NormService normService;

    private final ModelConversionService conversionService;

    @GetMapping
    public ResponseEntity<List<NormResponse>> getAll() {
        List<Norm> norms = normService.findAll();
        List<NormResponse> responses = conversionService.convert(norms, NormResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NormResponse> getOne(@PathVariable Long id) {
        Norm norm = normService.findById(id);
        NormResponse response = conversionService.convert(norm, NormResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"id"})
    public ResponseEntity<NormForRollResponse> getNormWithRolls(@RequestParam ("id") Long id){
        Norm norm = normService.findById(id);
        NormForRollResponse response = conversionService.convert(norm, NormForRollResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = "rollTypeId")
    public ResponseEntity<List<NormResponse>> getByRollTypeId(@RequestParam("rollTypeId") Long rollTypeId) {
        List<Norm> norms = normService.findNorms(rollTypeId);
        List<NormResponse> responses = conversionService.convert(norms, NormResponse.class);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_TECHNOLOGIST')")
    public ResponseEntity<NormResponse> save(@RequestBody NormRequest request) {
        Norm norm = conversionService.convert(request, Norm.class);
        normService.save(norm);
        NormResponse response = conversionService.convert(norm, NormResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_TECHNOLOGIST')")
    public ResponseEntity<NormResponse> update(@PathVariable Long id, @RequestBody NormRequest request) {
        Norm norm = conversionService.convert(request, Norm.class);
        norm.setId(id);
        normService.update(norm);
        NormResponse response = conversionService.convert(norm, NormResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_TECHNOLOGIST')")
    public ResponseEntity<NormResponse> delete(@PathVariable Long id) {
        normService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}