package ua.com.novopacksv.production.dto.plan;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.validator.ExistInDb;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MachinePlanRequest {

    @NotNull(message = "field machineNumber can't be empty!")
    @Max(value = 4, message = "There are ability to count 4 machines only!")
    private Integer machineNumber;

    @NotBlank(message = "field timeStart can't be empty!")
    private String timeStart;

    @NotNull(message = "field productTypeId can't be empty!")
    @ExistInDb(value = ProductType.class, message = "ProductType with this id does not exist!")
    private Long productTypeId;

    @NotNull(message = "field isImportant can't be empty!")
    private Boolean isImportant;

}
