package ua.com.novopacksv.production.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductBatchRequest {

    private Long productTypeId;

    private String creationDate;

    private Double amount;

}
