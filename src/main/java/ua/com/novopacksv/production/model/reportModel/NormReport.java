package ua.com.novopacksv.production.model.reportModel;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.model.rollModel.RollType;

@Data
@NoArgsConstructor
public class NormReport {

    private ProductType productType;

    private RollType rollType;

    private Integer rollAmount;

    private Integer productPlanAmount;

    private Integer productActualAmount;


}
