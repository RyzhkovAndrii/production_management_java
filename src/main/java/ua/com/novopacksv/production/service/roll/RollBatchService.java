package ua.com.novopacksv.production.service.roll;

import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.RollBatch;

import java.time.LocalDate;
import java.util.List;

public interface RollBatchService {

    List<RollBatch> createAll(LocalDate manufacturedDate);

    List<RollBatch> createAll(LocalDate manufacturedPeriodBegin, LocalDate manufacturedPeriodEnd);

    List<RollBatch> createAll(Long rollTypeId, LocalDate manufacturedPeriodBegin, LocalDate manufacturedPeriodEnd);

    RollBatch create(Long rollTypeId, LocalDate manufacturedDate) throws ResourceNotFoundException;

}