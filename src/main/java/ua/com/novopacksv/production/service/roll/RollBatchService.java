package ua.com.novopacksv.production.service.roll;

import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.RollBatch;

import java.time.LocalDate;
import java.util.List;

public interface RollBatchService {

    List<RollBatch> findAllByManufacturedDate(LocalDate date);

    List<RollBatch> findAllByRollTypeIdAndManufacturedPeriod(Long rollTypeId, LocalDate fromDate, LocalDate toDate);

    List<RollBatch> findAllByManufacturedPeriod(LocalDate fromDate, LocalDate toDate);

    RollBatch findByRollTypeIdAndManufacturedDate(Long rollTypeId, LocalDate date) throws ResourceNotFoundException;

}