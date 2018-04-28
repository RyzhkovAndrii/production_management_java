package ua.com.novopacksv.production.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOperationRequest {

    private String operationDate;

    private Long productTypeId;

    private String operationType;

    private Integer amount;
}
