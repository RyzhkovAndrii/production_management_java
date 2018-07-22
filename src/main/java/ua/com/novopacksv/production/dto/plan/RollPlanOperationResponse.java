package ua.com.novopacksv.production.dto.plan;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.dto.BaseEntityResponse;

import java.time.LocalDate;

@Getter
@Setter
public class RollPlanOperationResponse extends BaseEntityResponse {

    private LocalDate date;

    private Long rollTypeId;

    private Integer rollAmount;
}
