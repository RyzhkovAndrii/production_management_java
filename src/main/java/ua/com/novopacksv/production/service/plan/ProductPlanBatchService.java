package ua.com.novopacksv.production.service.plan;

import ua.com.novopacksv.production.model.planModel.ProductPlanBatch;

import java.time.LocalDate;
import java.util.List;

public interface ProductPlanBatchService {

    ProductPlanBatch getOne(Long productTypeId, LocalDate date);

    List<ProductPlanBatch> getAll(LocalDate date);
}
