package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.RollBatch;
import ua.com.novopacksv.production.model.rollModel.RollManufactured;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RollBatchServiceImpl implements RollBatchService {

    private final RollManufacturedService rollManufacturedService;

    @Override
    public RollBatch getOne(Long rollTypeId, LocalDate manufacturedDate) throws ResourceNotFoundException {
        RollManufactured rollManufactured = rollManufacturedService.findOne(manufacturedDate, rollTypeId);
        return getOne(rollManufactured);
    }

    @Override
    public List<RollBatch> getAll(LocalDate manufacturedDate) {
        List<RollManufactured> rollManufacturedList = rollManufacturedService.findAll(manufacturedDate);
        return getAll(rollManufacturedList);
    }

    @Override
    public List<RollBatch> getAll(LocalDate fromManufacturedDate, LocalDate toManufacturedDate) {
        List<RollManufactured> rollManufacturedList =
                rollManufacturedService.findAll(fromManufacturedDate, toManufacturedDate);
        return getAll(rollManufacturedList);
    }

    @Override
    public List<RollBatch> getAll(Long rollTypeId, LocalDate fromManufacturedDate, LocalDate toManufacturedDate) {
        List<RollManufactured> rollManufacturedList =
                rollManufacturedService.findAll(fromManufacturedDate, toManufacturedDate, rollTypeId);
        return getAll(rollManufacturedList);
    }

    private RollBatch getOne(RollManufactured rollManufactured) {
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
                .map(this::getOne)
                .collect(Collectors.toList());
    }

}