package ua.com.novopacksv.production.service.plan;

import ua.com.novopacksv.production.model.productModel.ProductLeftOver;

import java.time.LocalDate;
import java.util.List;

public interface ProductPlanLeftoverService {

    ProductLeftOver getOneWithoutPlan(Long productTypeId, LocalDate fromDate, LocalDate toDate);

    ProductLeftOver getOneTotal(Long productTypeId, LocalDate fromDate, LocalDate toDate);

    List<ProductLeftOver> getAllWithoutPlan(LocalDate fromDate, LocalDate toDate);

    List<ProductLeftOver> getAllTotal(LocalDate fromDate, LocalDate toDate);
}
