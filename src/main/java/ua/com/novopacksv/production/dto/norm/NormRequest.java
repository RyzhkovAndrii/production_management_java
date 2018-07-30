package ua.com.novopacksv.production.dto.norm;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.validator.ExistInDb;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
public class NormRequest {

    @NotNull
    private List<Long> rollTypeIds;

    @NotNull(message = "field productTypeId can't be empty!")
    @ExistInDb(value = ProductType.class, message = "ProductType with this id does not exist!")
    private Long productTypeId;

    @NotNull(message = "field norm can't be empty!")
    @Positive(message = "value of norm can't be negative!")
    private Integer norm;

    @NotNull(message = "field normForDay can't be empty!")
    @Positive(message = "value of normForDay can't be negative!")
    private Integer normForDay;
}
