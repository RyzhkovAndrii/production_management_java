package ua.com.novopacksv.production.service.roll;

import ua.com.novopacksv.production.model.rollModel.RollBatch;
import ua.com.novopacksv.production.service.BaseEntityService;

import java.time.LocalDate;
import java.util.List;

public interface RollBatchService extends BaseEntityService<RollBatch> {

    List<RollBatch> findAllByManufacturedDate(LocalDate date);

    List<RollBatch> findAllByRollTypeIdAndManufacturedPeriod(Long rollTypeId, LocalDate fromDate, LocalDate toDate);

    List<RollBatch> findAllByManufacturedPeriod(LocalDate fromDate, LocalDate toDate);

    RollBatch findByRollTypeIdAndManufacturedDate(Long rollTypeId, LocalDate date);

}