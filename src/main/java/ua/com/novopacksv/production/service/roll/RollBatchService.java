package ua.com.novopacksv.production.service.roll;

import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.RollBatch;

import java.time.LocalDate;
import java.util.List;

public interface RollBatchService {

    List<RollBatch> getAll(LocalDate manufacturedDate);

    List<RollBatch> getAll(LocalDate manufacturedPeriodBegin, LocalDate manufacturedPeriodEnd);

    List<RollBatch> getAll(Long rollTypeId, LocalDate manufacturedPeriodBegin, LocalDate manufacturedPeriodEnd);

    RollBatch get(Long rollTypeId, LocalDate manufacturedDate) throws ResourceNotFoundException;

}