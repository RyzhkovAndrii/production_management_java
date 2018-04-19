package ua.com.novopacksv.production.dto.roll;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.validator.Unique;

import javax.validation.constraints.*;

@Getter
@Setter
public class RollTypeRequest {

    @NotBlank(message = "Roll type's name is a required field!")
    @Size(max = 50, message = "Roll type's name must be less then 50 symbols long!")
    @Unique(value = RollType.class, column = "name", message = "Roll type's name must be unique!")
    private String name;

    @NotNull(message = "Roll type's thickness is a required field!")
    @Positive(message = "Roll type's thickness must be greater then 0!")
    private Double thickness;

    @NotNull(message = "Roll type's weight is a required field!")
    @Positive(message = "Roll type's weight must be greater then 0!")
    private Double weight;

    @NotNull(message = "Roll type's color code is a required field!")
    @Size(min = 7, max = 7, message = "Incorrect roll type's color code format!")
    @Pattern(regexp = "#([0-9A-Fa-f]{6})")
    private String colorCode;

}