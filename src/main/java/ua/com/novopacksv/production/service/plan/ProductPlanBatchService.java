package ua.com.novopacksv.production.service.plan;

import ua.com.novopacksv.production.model.planModel.ProductPlanBatch;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ProductPlanBatchService {

    ProductPlanBatch getOne(Long productTypeId, LocalDate date);

    List<ProductPlanBatch> getAll(LocalDate date);

    Map<Long, List<ProductPlanBatch>> getAll(LocalDate fromDate, LocalDate toDate);

    void equalizePlanToMachinePlan(Long productTypeId, LocalDate date);
}
