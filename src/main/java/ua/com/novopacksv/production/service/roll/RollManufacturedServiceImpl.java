package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Scheduled;
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

    private final static Integer PERIOD_IN_DAYS_FOR_CHECK_ROLL_READY_TO_USE = 7;

    private final RollManufacturedRepository rollManufacturedRepository;

    private final RollOperationService rollOperationService;

    private final ConversionService conversionService;

    private boolean isRollReadyToUse(LocalDate manufacturedDate) {
        LocalDate now = LocalDate.now();
        return manufacturedDate
                .plusDays(RollType.ROLL_WAITING_PERIOD_IN_DAYS - 1)
                .isBefore(now);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void rollsBecomeReadyToUseForNow() {
        findAllNotHaveReadyToUseStatusButShouldHave().forEach(rollManufactured -> {
            rollManufactured.setReadyToUse(true);
            rollManufacturedRepository.save(rollManufactured);
        });
    }

    private List<RollManufactured> findAllNotHaveReadyToUseStatusButShouldHave() {
        LocalDate manufacturedDateForRollsWhichBecomeReadyToUseForToday
                = LocalDate.now().minusDays(RollType.ROLL_WAITING_PERIOD_IN_DAYS);
        return rollManufacturedRepository.findAllByManufacturedDateBetweenAndReadyToUseIsFalse(
                manufacturedDateForRollsWhichBecomeReadyToUseForToday.minusDays(PERIOD_IN_DAYS_FOR_CHECK_ROLL_READY_TO_USE),
                manufacturedDateForRollsWhichBecomeReadyToUseForToday);
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
        return rollManufacturedRepository.findAllByManufacturedDate(date);
    }

    @Override
    public List<RollManufactured> findAllByManufacturedPeriod(LocalDate fromDate, LocalDate toDate) {
        return rollManufacturedRepository.findAllByManufacturedDateBetween(fromDate, toDate);
    }

    @Override
    public List<RollManufactured> findAllByManufacturedPeriodAndRollType(
            LocalDate fromDate, LocalDate toDate, RollType rollType) {
        return rollManufacturedRepository.findAllByManufacturedDateBetweenAndRollType(fromDate, toDate, rollType);
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