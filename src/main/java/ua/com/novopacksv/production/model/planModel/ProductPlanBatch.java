package ua.com.novopacksv.production.model.planModel;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.productModel.ProductType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ProductPlanBatch {

    private LocalDate date;

    private ProductType productType;

    private Integer amount;
}
