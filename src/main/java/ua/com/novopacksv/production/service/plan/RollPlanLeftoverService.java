package ua.com.novopacksv.production.service.plan;

import ua.com.novopacksv.production.model.rollModel.RollLeftOver;

import java.time.LocalDate;
import java.util.List;

public interface RollPlanLeftoverService {

    RollLeftOver getOneWithoutPlan(Long rollTypeId, LocalDate fromDate, LocalDate toDate);

    RollLeftOver getOneTotal(Long rollTypeId, LocalDate fromDate, LocalDate toDate);

    List<RollLeftOver> getAllWithoutPlan(LocalDate fromDate, LocalDate toDate);

    List<RollLeftOver> getAllTotal(LocalDate fromDate, LocalDate toDate);
}
