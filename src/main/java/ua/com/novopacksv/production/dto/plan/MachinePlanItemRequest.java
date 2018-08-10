package ua.com.novopacksv.production.dto.plan;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.validator.ExistInDb;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class MachinePlanItemRequest {

    @NotNull(message = "roll type id is a required field!")
    @ExistInDb(value = RollType.class, message = "there are no roll types with this id!")
    private Long rollTypeId;

    @NotNull(message = "roll amount is a required field!")
    @Positive(message = "roll amount must be greater then 0!")
    private Integer rollAmount;

    @NotNull(message = "product amount is a required field!")
    @Positive(message = "product amount must be greater then 0!")
    private Integer productAmount;

}
