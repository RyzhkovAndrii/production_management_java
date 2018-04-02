package ua.com.novopacksv.production.dto.product;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.dto.BaseEntityResponse;

@Getter
@Setter
public class ProductTypeResponse extends BaseEntityResponse {

    private String name;

    private Double weight;

    private String colorCode;

}
