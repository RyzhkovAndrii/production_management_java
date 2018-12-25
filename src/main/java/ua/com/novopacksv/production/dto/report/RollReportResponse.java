package ua.com.novopacksv.production.dto.report;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RollReportResponse {

    private Long rollTypeId;

    private Integer planAmount;

    private Integer actualAmount;

}
