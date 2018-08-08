package ua.com.novopacksv.production.dto.plan;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.validator.ExistInDb;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class MachinePlanRequest {

    @NotNull(message = "field machineNumber can't be empty!")
    @Max(value = 4, message = "There are ability to count 4 machines only!")
    private Integer machineNumber;

    @NotBlank(message = "field timeStart can't be empty!")
    private String timeStart;

    @NotNull(message = "field can't be empty!")
    @ExistInDb(value = ProductType.class, message = "ProductType with this id does not exist!")
    private Long productTypeId;

    @NotNull(message = "field productAmount can't be empty!")
    @Positive(message = "value of productAmount can't be negative!")
    private Integer productAmount;

    @NotNull(message = "field isImportant can't be null!")
    private Boolean isImportant;

}
