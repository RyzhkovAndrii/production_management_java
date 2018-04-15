package ua.com.novopacksv.production.service.roll;

import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.RollBatch;

import java.time.LocalDate;
import java.util.List;

public interface RollBatchService {

    RollBatch getOne(Long rollTypeId, LocalDate manufacturedDate) throws ResourceNotFoundException;

    List<RollBatch> getAll(LocalDate manufacturedDate);

    List<RollBatch> getAll(LocalDate fromManufacturedDate, LocalDate toManufacturedDate);

    List<RollBatch> getAll(Long rollTypeId, LocalDate fromManufacturedDate, LocalDate toManufacturedDate);

}