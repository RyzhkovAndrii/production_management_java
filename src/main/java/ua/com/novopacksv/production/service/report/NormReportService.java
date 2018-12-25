package ua.com.novopacksv.production.service.report;

import ua.com.novopacksv.production.model.reportModel.NormReport;

import java.time.LocalDate;
import java.util.List;

public interface NormReportService {

    List<NormReport> getAll(LocalDate from, LocalDate to);

}
