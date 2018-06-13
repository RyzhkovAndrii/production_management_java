package ua.com.novopacksv.production.dto.plan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPlanOperationRequest {

    private String date;

    private Long productTypeId;

    private Long rollTypeId;

    private Integer rollAmount;

    private Integer productAmount;
}
