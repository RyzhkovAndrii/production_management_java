package ua.com.novopacksv.production.dto.roll;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.dto.BaseEntityResponse;
import ua.com.novopacksv.production.model.productModel.ProductType;

@Getter
@Setter
public class RollOperationResponseWithProduct extends BaseEntityResponse {

    private String operationDate;

    private String operationType;

    private String manufacturedDate;

    private Long rollTypeId;

    private Integer rollAmount;

    private ProductType productType;
}
