package ua.com.novopacksv.production.scheduling.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.service.roll.RollManufacturedService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class RollReadyToUseTask extends Task {

    @Autowired
    private RollManufacturedService rollManufacturedService;

    @Override
    public void run() {
        LocalDateTime lastExecutionTime = this.getTaskDetails().getLastExecutionTime();
        LocalDate from = lastExecutionTime == null
                ? LocalDate.ofEpochDay(0)
                : lastExecutionTime.minusDays(RollType.READY_TO_USE_PERIOD).toLocalDate();
        LocalDate to = LocalDate.now().minusDays(RollType.READY_TO_USE_PERIOD);
        rollManufacturedService.setReadyToUseTrue(from, to);
    }

}