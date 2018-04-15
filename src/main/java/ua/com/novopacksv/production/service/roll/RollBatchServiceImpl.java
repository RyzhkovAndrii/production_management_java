package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
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
    public List<RollBatch> getAll(LocalDate manufacturedDate) {
        List<RollManufactured> rollManufacturedList =
                rollManufacturedService.findAllByManufacturedDate(manufacturedDate);
        return this.getAll(rollManufacturedList);
    }

    @Override
    public List<RollBatch> getAll(Long rollTypeId, LocalDate manufacturedPeriodBegin, LocalDate manufacturedPeriodEnd) {
        RollType rollType = rollTypeService.findById(rollTypeId);
        List<RollManufactured> rollManufacturedList = rollManufacturedService
                .findAllByManufacturedPeriodAndRollType(manufacturedPeriodBegin, manufacturedPeriodEnd, rollType);
        return this.getAll(rollManufacturedList);
    }

    @Override
    public List<RollBatch> getAll(LocalDate manufacturedPeriodBegin, LocalDate manufacturedPeriodEnd) {
        return this.getAll(rollManufacturedService
                .findAllByManufacturedPeriod(manufacturedPeriodBegin, manufacturedPeriodEnd));
    }

    @Override
    public RollBatch get(Long rollTypeId, LocalDate manufacturedDate) throws ResourceNotFoundException {
        RollType rollType = rollTypeService.findById(rollTypeId);
        RollManufactured rollManufactured =
                rollManufacturedService.findByManufacturedDateAndRollType(manufacturedDate, rollType);
        return this.get(rollManufactured);
    }

    private RollBatch get(RollManufactured rollManufactured) {
        Integer manufacturedAmount = rollManufacturedService.getManufacturedRollAmount(rollManufactured);
        Integer usedAmount = rollManufacturedService.getUsedRollAmount(rollManufactured);
        RollBatch rollBatch = new RollBatch();
        rollBatch.setRollManufactured(rollManufactured);
        rollBatch.setManufacturedAmount(manufacturedAmount);
        rollBatch.setUsedAmount(usedAmount);
        return rollBatch;
    }

    private List<RollBatch> getAll(List<RollManufactured> rollManufacturedList) {
        return rollManufacturedList.stream()
                .map(this::get)
                .collect(Collectors.toList());
    }

}