package ua.com.novopacksv.production.dto.roll;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.model.rollModel.CheckStatus;
import ua.com.novopacksv.production.validator.EnumValue;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RollCheckRequest {

    @NotBlank(message = "roll left over check status is a required field!")
    @EnumValue(value = CheckStatus.class, message = "roll left over check status is not found!")
    private String rollLeftOverCheckStatus;

}