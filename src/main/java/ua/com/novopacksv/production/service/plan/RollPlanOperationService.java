package ua.com.novopacksv.production.service.plan;

import ua.com.novopacksv.production.model.planModel.RollPlanOperation;
import ua.com.novopacksv.production.service.BaseEntityService;

import java.time.LocalDate;
import java.util.List;

public interface RollPlanOperationService extends BaseEntityService<RollPlanOperation>{

    List<RollPlanOperation> findAll(Long rollTypeId, LocalDate fromDate, LocalDate toDate);
}
