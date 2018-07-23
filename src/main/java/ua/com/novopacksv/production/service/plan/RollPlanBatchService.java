package ua.com.novopacksv.production.service.plan;

import ua.com.novopacksv.production.model.planModel.RollPlanBatch;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface RollPlanBatchService {

    RollPlanBatch getOne(Long rollTypeId, LocalDate date);

    List<RollPlanBatch> getAll(LocalDate date);

    Map<Long, List<RollPlanBatch>> getAll(LocalDate fromDate, LocalDate toDate);
}
