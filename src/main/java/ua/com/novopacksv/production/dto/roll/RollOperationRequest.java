package ua.com.novopacksv.production.dto.roll;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import ua.com.novopacksv.production.model.rollModel.OperationType;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.validator.EnumValue;
import ua.com.novopacksv.production.validator.ExistInDb;
import ua.com.novopacksv.production.validator.LocalDateFormat;
import ua.com.novopacksv.production.validator.PastOrPresent;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class RollOperationRequest {

    @NotBlank(message = "roll operation date is a required field!")
    @LocalDateFormat(message = "incorrect roll operation date format!")
    @PastOrPresent(message = "roll operation date can not be in future!")
    private String operationDate;

    @NotBlank(message = "roll operation type is a required field!")
    @EnumValue(value = OperationType.class, message = "roll operation type is not found!")
    private String operationType;

    @NotBlank(message = "roll manufacture date is a required field!")
    @LocalDateFormat(message = "incorrect roll manufacture date format!")
    @PastOrPresent(message = "roll manufacture date can not be in future!")
    private String manufacturedDate;

    @NotNull(message = "roll type id is a required field!")
    @ExistInDb(value = RollType.class, message = "there are no roll types with this id!")
    private Long rollTypeId;

    @NotNull(message = "roll amount is a required field!")
    @Positive(message = "roll amount must be greater then 0!")
    private Integer rollAmount;

    @Nullable
    private Long productTypeIdForUseOperation;

}