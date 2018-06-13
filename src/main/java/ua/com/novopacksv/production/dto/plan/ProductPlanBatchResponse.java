package ua.com.novopacksv.production.dto.plan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPlanBatchResponse {

    private String date;

    private Long productTypeId;

    private Integer amount;
}
