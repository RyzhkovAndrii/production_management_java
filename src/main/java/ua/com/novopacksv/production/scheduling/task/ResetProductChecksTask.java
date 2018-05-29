package ua.com.novopacksv.production.scheduling.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.service.product.ProductCheckService;

@Component
public class ResetProductChecksTask extends Task {

    @Autowired
    private ProductCheckService productCheckService;

    @Override
    public void run() {
        productCheckService.setNotCheckedStatusForAll();
    }
}
