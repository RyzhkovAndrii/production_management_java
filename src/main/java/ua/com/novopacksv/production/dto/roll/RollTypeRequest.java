package ua.com.novopacksv.production.dto.roll;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.validator.Unique;

import javax.validation.constraints.*;

@Getter
@Setter
public class RollTypeRequest {

    @NotBlank(message = "roll type's name is a required field!")
    @Size(max = 50, message = "roll type's name must be less then 50 symbols long!")
    @Unique(value = RollType.class, column = "name", message = "roll type's name must be unique!")
    private String name;

    @NotNull(message = "roll's thickness is a required field!")
    @Positive(message = "roll's thickness must be greater then 0!")
    private Double thickness;

    @NotNull(message = "roll's weight is a required field!")
    @Positive(message = "roll's weight must be greater then 0!")
    private Double weight;

    @NotNull(message = "roll's color code is a required field!")
    @Pattern(regexp = "#([0-9A-Fa-f]{6})", message = "incorrect roll's color code format!")
    private String colorCode;

}