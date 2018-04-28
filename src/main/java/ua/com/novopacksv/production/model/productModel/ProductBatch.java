package ua.com.novopacksv.production.model.productModel;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ProductBatch {

    private ProductType productType;

    private Integer manufacturedAmount;

    private Integer soldAmount;

}
