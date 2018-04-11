package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.rollModel.RollManufactured;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.repository.rollRepository.RollManufacturedRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RollManufacturedServiceImpl implements RollManufacturedService {

    private final RollManufacturedRepository rollManufacturedRepository;

    private final Integer rollReadyToUseDaysPeriod = 14; // todo move to madel ???

    private boolean isRollReadyToUse(LocalDate manufacturedDate) {
        LocalDate now = LocalDate.now();
        return manufacturedDate
                .plusDays(rollReadyToUseDaysPeriod - 1)
                .isBefore(now);
    }

    // todo every day in 00-00
    private void rollsBecomeReadyToUseForNow() { // todo another method name
        LocalDate manufacturedDateWhenRollsBecomeReadyToUseForNow
                = LocalDate.now().minusDays(rollReadyToUseDaysPeriod);
        rollManufacturedRepository
                .findAllByManufacturedDate(manufacturedDateWhenRollsBecomeReadyToUseForNow) // todo period 7 days
                .forEach(rollManufactured -> {
                    rollManufactured.setReadyToUse(true);
                    rollManufacturedRepository.save(rollManufactured);
                });
    }

    @Override
    public RollManufactured findByManufacturedDateAndRollTypeOrCreateNew(LocalDate manufacturedDate, RollType rollType) {
        RollManufactured rollManufactured = rollManufacturedRepository
                .findAllByManufacturedDateAndRollType(manufacturedDate, rollType);
        if (rollManufactured == null) {
            rollManufactured = new RollManufactured();
            rollManufactured.setManufacturedDate(manufacturedDate);
            rollManufactured.setRollType(rollType);
            rollManufactured.setReadyToUse(isRollReadyToUse(manufacturedDate));
            rollManufacturedRepository.save(rollManufactured);
        }
        return rollManufactured;
    }

    @Override
    public RollManufactured findById(Long id) {
        return null;
    }

    @Override
    public List<RollManufactured> findAll() {
        return null;
    }

    @Override
    public RollManufactured save(RollManufactured rollManufactured) {
        return null;
    }

    @Override
    public RollManufactured update(RollManufactured rollManufactured) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

}