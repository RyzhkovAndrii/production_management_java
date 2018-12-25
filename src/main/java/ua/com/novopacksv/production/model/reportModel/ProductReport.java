package ua.com.novopacksv.production.model.reportModel;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.productModel.ProductType;

@Data
@NoArgsConstructor
public class ProductReport {

    private ProductType productType;

    private Integer planAmount;

    private Integer actualAmount;

}
