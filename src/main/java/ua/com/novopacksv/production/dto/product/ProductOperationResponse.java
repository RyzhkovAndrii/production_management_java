package ua.com.novopacksv.production.dto.product;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.dto.BaseEntityResponse;

@Getter
@Setter
public class ProductOperationResponse extends BaseEntityResponse {

    private String operationDate;

    private Long productTypeId;

    private String operationType;

    private Integer amount;
}
