package ua.com.novopacksv.production.dto.plan;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RollPlanOperationRequest {

    private LocalDate date;

    private Long rollTypeId;

    private Integer rollAmount;
}
