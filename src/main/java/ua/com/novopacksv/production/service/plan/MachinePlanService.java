package ua.com.novopacksv.production.service.plan;

import ua.com.novopacksv.production.model.planModel.MachinePlan;
import ua.com.novopacksv.production.service.BaseEntityService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MachinePlanService extends BaseEntityService<MachinePlan> {

    Double getDuration(MachinePlan machinePlan);

    List<MachinePlan> findByMachineNumberAndDate(Integer machineNumber, LocalDate date);

    List<MachinePlan> findSort(Integer machineNumber, LocalDate date, String sort);
}
