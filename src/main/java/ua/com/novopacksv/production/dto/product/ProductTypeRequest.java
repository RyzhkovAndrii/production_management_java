package ua.com.novopacksv.production.dto.product;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProductTypeRequest {

    @Size(max = 20, message = "product type should be shorter then 20 symbols!")
    private String name;

    @NotNull(message = "field weight can't be empty!")
    @Positive(message = "number should be positive!")
    private Double weight;

    @NotNull(message = "field colorCode can't be empty!")
    @Pattern(regexp = "#([0-9A-Fa-f]{6})", message = "incorrect color code format!")
    private String colorCode;

}
