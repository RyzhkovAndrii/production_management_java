package ua.com.novopacksv.production.rest.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.novopacksv.production.converter.ModelConversionService;
import ua.com.novopacksv.production.dto.report.RollReportResponse;
import ua.com.novopacksv.production.model.reportModel.RollReport;
import ua.com.novopacksv.production.service.report.RollReportService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "${spring.rest.api-url-prefix}/roll-reports", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@PreAuthorize("hasAnyRole('ROLE_CMO', 'ROLE_CTO', 'ROLE_ECONOMIST', 'ROLE_CEO', 'ROLE_FULL_ACCESS')")
@RequiredArgsConstructor
public class RollReportController {

    private final RollReportService rollReportService;

    private final ModelConversionService conversionService;

    @GetMapping(params = {"from", "to"})
    public ResponseEntity<List<RollReportResponse>> getAll(@RequestParam("from") LocalDate from,
                                                           @RequestParam("to") LocalDate to) {
        List<RollReport> rollReports = rollReportService.getAll(from, to);
        List<RollReportResponse> response = conversionService.convert(rollReports, RollReportResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
