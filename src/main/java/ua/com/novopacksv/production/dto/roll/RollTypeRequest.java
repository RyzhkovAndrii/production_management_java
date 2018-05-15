package ua.com.novopacksv.production.dto.roll;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class RollTypeRequest {

    @Size(max = 20, message = "roll type note must be less then 20 symbols long!")
    private String note;

    @NotNull(message = "roll thickness is a required field!")
    @Positive(message = "roll thickness must be greater then 0!")
    private Double thickness;

    @NotNull(message = "roll min weight is a required field!")
    @Positive(message = "roll min weight must be greater then 0!")
    private Double minWeight;

    @NotNull(message = "roll max weight is a required field!")
    @Positive(message = "roll max weight must be greater then 0!")
    private Double maxWeight;

    @NotNull(message = "roll length is a required field!")
    @Positive(message = "roll length must be greater then 0!")
    private Double length;

    @NotNull(message = "roll color code is a required field!")
    @Pattern(regexp = "#([0-9A-Fa-f]{6})", message = "incorrect roll color code format!")
    private String colorCode;

}