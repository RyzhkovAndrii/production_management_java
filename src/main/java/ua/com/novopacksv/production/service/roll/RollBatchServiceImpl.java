package ua.com.novopacksv.production.service.roll;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.rollModel.RollBatch;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class RollBatchServiceImpl implements RollBatchService {

    @Override
    public List<RollBatch> findAllByManufacturedDate(LocalDate date) {
        return null;
    }

    @Override
    public List<RollBatch> findAllByRollTypeIdAndManufacturedPeriod(Long rollTypeId, LocalDate fromDate, LocalDate toDate) {
        return null;
    }

    @Override
    public List<RollBatch> findAllByManufacturedPeriod(LocalDate fromDate, LocalDate toDate) {
        return null;
    }

    @Override
    public RollBatch findByRollTypeIdAndManufacturedDate(Long rollTypeId, LocalDate date) {
        return null;
    }

}