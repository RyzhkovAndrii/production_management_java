package ua.com.novopacksv.production.dto.plan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RollPlanBatchResponse {

    private String date;

    private Long rollTypeId;

    private Integer usedAmount;

    private Integer manufacturedAmount;
}
