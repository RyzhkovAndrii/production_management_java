package ua.com.novopacksv.production.scheduling.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.service.roll.RollCheckService;

@Component
public class ResetRollChecksTask extends Task {

    @Autowired
    private RollCheckService rollCheckService;

    @Override
    public void run() {
        rollCheckService.setNotCheckedStatusForAll();
    }

}