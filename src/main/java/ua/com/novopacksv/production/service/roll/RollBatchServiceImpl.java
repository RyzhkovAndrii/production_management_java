package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.rollModel.RollBatch;
import ua.com.novopacksv.production.model.rollModel.RollManufactured;
import ua.com.novopacksv.production.model.rollModel.RollType;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RollBatchServiceImpl implements RollBatchService {

    private final RollTypeService rollTypeService;

    private final RollManufacturedService rollManufacturedService;

    @Override
    public List<RollBatch> findAllByManufacturedDate(LocalDate date) {
        return getAllFromRollManufacturedList(rollManufacturedService.findAllByManufacturedDate(date));
    }

    @Override
    public List<RollBatch> findAllByRollTypeIdAndManufacturedPeriod(
            Long rollTypeId, LocalDate fromDate, LocalDate toDate) {
        List<RollManufactured> rollManufacturedList =
                rollManufacturedService.findAllByRollTypeIdAndManufacturedPeriod(rollTypeId, fromDate, toDate);
        return getAllFromRollManufacturedList(rollManufacturedList);
    }

    @Override
    public List<RollBatch> findAllByManufacturedPeriod(LocalDate fromDate, LocalDate toDate) {
        return getAllFromRollManufacturedList(rollManufacturedService.findAllByManufacturedPeriod(fromDate, toDate));
    }

    @Override
    public RollBatch findByRollTypeIdAndManufacturedDate(Long rollTypeId, LocalDate date) {
        RollType rollType = rollTypeService.findById(rollTypeId);
        RollManufactured rollManufactured =
                rollManufacturedService.findByManufacturedDateAndRollTypeOrCreateNew(date, rollType);
        return getByRollManufactured(rollManufactured);
    }

    private RollBatch getByRollManufactured(RollManufactured rollManufactured) {
        Integer manufacturedAmount = rollManufacturedService.getManufacturedRollAmount(rollManufactured);
        Integer usedAmount = rollManufacturedService.getUsedRollAmount(rollManufactured);
        RollBatch rollBatch = new RollBatch();
        rollBatch.setRollManufactured(rollManufactured);
        rollBatch.setManufacturedAmount(manufacturedAmount);
        rollBatch.setUsedAmount(usedAmount);
        return rollBatch;
    }

    private List<RollBatch> getAllFromRollManufacturedList(List<RollManufactured> rollManufacturedList) {
        return rollManufacturedList.stream()
                .map(this::getByRollManufactured)
                .collect(Collectors.toList());
    }

}