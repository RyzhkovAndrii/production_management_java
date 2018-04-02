package ua.com.novopacksv.production.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductLeftOverRequest {

    private String productTypeId;

    private Integer amount;

}
