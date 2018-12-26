package ua.com.novopacksv.production.service.report;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.planModel.ProductPlanOperation;
import ua.com.novopacksv.production.model.productModel.ProductOperation;
import ua.com.novopacksv.production.model.productModel.ProductOperationType;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.model.reportModel.ProductReport;
import ua.com.novopacksv.production.repository.productRepository.ProductTypeRepository;
import ua.com.novopacksv.production.service.plan.ProductPlanOperationService;
import ua.com.novopacksv.production.service.product.ProductOperationService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductReportServiceImpl implements ProductReportService {

    private final ProductTypeRepository productTypeRepository;

    private final ProductOperationService productOperationService;

    private final ProductPlanOperationService productPlanOperationService;

    @Override
    public List<ProductReport> getAll(LocalDate from, LocalDate to) {
        return productTypeRepository
                .findAll()
                .stream()
                .map(type -> getReport(type, from, to))
                .filter(this::isReportNotEmpty)
                .collect(Collectors.toList());
    }

    private ProductReport getReport(ProductType type, LocalDate from, LocalDate to) {
        Integer actual = getActualAmount(type, from, to);
        Integer plan = getPlanAmount(type, from, to);
        ProductReport report = new ProductReport();
        report.setProductType(type);
        report.setActualAmount(actual);
        report.setPlanAmount(plan);
        return report;
    }

    private Integer getActualAmount(ProductType type, LocalDate from, LocalDate to) {
        return productOperationService
                .findAllOperationBetweenDatesByTypeId(type.getId(), from, to)
                .stream()
                .filter(operation -> operation.getProductOperationType().equals(ProductOperationType.MANUFACTURED))
                .mapToInt(ProductOperation::getAmount)
                .sum();
    }

    private Integer getPlanAmount(ProductType type, LocalDate from, LocalDate to) {
        return productPlanOperationService
                .getAll(type.getId(), from, to)
                .stream()
                .mapToInt(ProductPlanOperation::getProductAmount)
                .sum();
    }

    private boolean isReportNotEmpty(ProductReport report) {
        return report.getActualAmount() != 0 || report.getPlanAmount() != 0;
    }

}
