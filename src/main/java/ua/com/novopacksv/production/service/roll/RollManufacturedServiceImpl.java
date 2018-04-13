package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.RollManufactured;
import ua.com.novopacksv.production.model.rollModel.RollOperation;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.repository.rollRepository.RollManufacturedRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RollManufacturedServiceImpl implements RollManufacturedService {

    private final RollManufacturedRepository rollManufacturedRepository;

    private final RollOperationService rollOperationService;

    private final ConversionService conversionService;

    private boolean isRollReadyToUse(LocalDate manufacturedDate) {
        LocalDate now = LocalDate.now();
        return manufacturedDate
                .plusDays(RollType.READY_TO_USE_DAYS_PERIOD - 1)
                .isBefore(now);
    }

    // todo every day in 00-00
    private void rollsBecomeReadyToUseForNow() { // todo another method name
        LocalDate manufacturedDateWhenRollsBecomeReadyToUseForNow
                = LocalDate.now().minusDays(RollType.READY_TO_USE_DAYS_PERIOD);
        rollManufacturedRepository
                .findAllByManufacturedDate(manufacturedDateWhenRollsBecomeReadyToUseForNow) // todo period 7 days
                .forEach(rollManufactured -> {
                    rollManufactured.setReadyToUse(true);
                    rollManufacturedRepository.save(rollManufactured);
                });
    }

    @Override
    public RollManufactured findByManufacturedDateAndRollTypeOrCreateNew(LocalDate manufacturedDate, RollType rollType) {
        return rollManufacturedRepository.findByManufacturedDateAndRollType(manufacturedDate, rollType)
                .orElseGet(() -> {
                    RollManufactured rollManufactured = new RollManufactured();
                    rollManufactured.setManufacturedDate(manufacturedDate);
                    rollManufactured.setRollType(rollType);
                    rollManufactured.setReadyToUse(isRollReadyToUse(manufacturedDate));
                    rollManufacturedRepository.save(rollManufactured); // TODO need save??? or hibernate save it when save roll operation
                    return rollManufactured;
                });
    }

    @Override
    public RollManufactured findByManufacturedDateAndRollType(LocalDate manufacturedDate, RollType rollType)
            throws ResourceNotFoundException {
        return rollManufacturedRepository.findByManufacturedDateAndRollType(manufacturedDate, rollType)
                .orElseThrow(() -> {
                    String formatDate = conversionService.convert(manufacturedDate, String.class);
                    String message = String.format("Roll manufactured whit roll type id = %d" +
                            " and manufactured date = %s not found!", rollType.getId(), formatDate);
                    return new ResourceNotFoundException(message);
                });
    }

    @Override
    public List<RollManufactured> findAllByManufacturedDate(LocalDate date) {
        return null;
    }

    @Override
    public List<RollManufactured> findAllByManufacturedPeriod(LocalDate fromDate, LocalDate toDate) {
        return null;
    }

    @Override
    public List<RollManufactured> findAllByRollTypeIdAndManufacturedPeriod(Long rollTypeId, LocalDate fromDate, LocalDate toDate) {
        return null;
    }

    @Override
    public Integer getManufacturedRollAmount(RollManufactured rollManufactured) {
        return rollOperationService.getAllManufacturedOperationsByRollManufactured(rollManufactured)
                .stream()
                .mapToInt(RollOperation::getRollAmount)
                .sum();
    }

    @Override
    public Integer getUsedRollAmount(RollManufactured rollManufactured) {
        return rollOperationService.getAllUsedOperationsByRollManufactured(rollManufactured)
                .stream()
                .mapToInt(RollOperation::getRollAmount)
                .sum();
    }

}