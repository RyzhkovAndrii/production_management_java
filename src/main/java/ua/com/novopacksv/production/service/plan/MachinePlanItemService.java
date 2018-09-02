package ua.com.novopacksv.production.service.plan;

import ua.com.novopacksv.production.model.planModel.MachinePlan;
import ua.com.novopacksv.production.model.planModel.MachinePlanItem;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.service.BaseEntityService;

public interface MachinePlanItemService extends BaseEntityService<MachinePlanItem> {

    MachinePlanItem findOne(MachinePlan machinePlan, RollType rollType);

}
