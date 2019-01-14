package ua.com.novopacksv.production.dto.report;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NormReportResponse {

    private Long productTypeId;

    private List<RollOperationResponse> rolls;

    private Integer productPlanAmount;

    private Integer productActualAmount;

}
