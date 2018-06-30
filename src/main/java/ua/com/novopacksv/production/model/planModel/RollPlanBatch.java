package ua.com.novopacksv.production.model.planModel;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.rollModel.RollType;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class RollPlanBatch {

    private LocalDate date;

    private RollType rollType;

    private Integer rollPlanManufacturedAmount;

    private Integer rollPlanUsedAmount;
}
