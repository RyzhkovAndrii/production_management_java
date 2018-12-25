package ua.com.novopacksv.production.service.report;

import ua.com.novopacksv.production.model.reportModel.ProductReport;

import java.time.LocalDate;
import java.util.List;

public interface ProductReportService {

    List<ProductReport> getAll(LocalDate from, LocalDate to);
}
