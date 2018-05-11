package ua.com.novopacksv.production.dto.product;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.model.rollModel.CheckStatus;
import ua.com.novopacksv.production.validator.EnumValue;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProductCheckRequest  {

    @NotBlank(message = "product left over check status is a required field!")
    @EnumValue(value = CheckStatus.class, message = "product left over check status is not found!")
    private String productLeftOverCheckStatus;
}
