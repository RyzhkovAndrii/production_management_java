package ua.com.novopacksv.production.dto.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NormReportResponse {

    private Long productTypeId;

    private Long rollTypeId;

    private Integer rollAmount;

    private Integer productPlanAmount;

    private Integer productActualAmount;

}
