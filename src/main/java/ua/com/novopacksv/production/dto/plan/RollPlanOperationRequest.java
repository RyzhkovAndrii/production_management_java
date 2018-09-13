package ua.com.novopacksv.production.dto.plan;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.validator.ExistInDb;
import ua.com.novopacksv.production.validator.LocalDateFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
@Setter
public class RollPlanOperationRequest {

    @NotBlank(message = "field date can't be empty!")
    @LocalDateFormat(message = "incorrect operation date format!")
    @Future(message = "date should be in future!")
    private String date;

    @NotNull(message = "field rollTypeId can't be empty!")
    @ExistInDb(value = RollType.class, message = "RollType with this id does not exist!")
    private Long rollTypeId;

    @NotNull(message = "field rollAmount can't be empty!")
    @Positive(message = "value of rollAmount can't be negative!")
    private Integer rollAmount;
}
