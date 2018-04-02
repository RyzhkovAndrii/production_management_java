package ua.com.novopacksv.production.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductTypeRequest {

    private String name;

    private Double weight;

    private String colorCode;

}
