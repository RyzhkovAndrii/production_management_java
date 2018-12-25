package ua.com.novopacksv.production.dto.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductReportResponse {

    private Long productTypeId;

    private Integer planAmount;

    private Integer actualAmount;

}
