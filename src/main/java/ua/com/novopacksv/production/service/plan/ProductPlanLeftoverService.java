package ua.com.novopacksv.production.service.plan;

import ua.com.novopacksv.production.model.productModel.ProductLeftOver;

import java.time.LocalDate;
import java.util.List;

public interface ProductPlanLeftoverService {

    ProductLeftOver getOneWithoutPlan(Long productTypeId, LocalDate date);

    ProductLeftOver getOneTotal(Long productTypeId, LocalDate date);

    List<ProductLeftOver> getAllWithoutPlan(LocalDate date);

    List<ProductLeftOver> getAllTotal(LocalDate date);
}
