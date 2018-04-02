package ua.com.novopacksv.production.dto.product;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.dto.BaseEntityResponse;

@Getter
@Setter
public class ProductLeftOverResponse extends BaseEntityResponse {

    private Long productTypeId;

    private Integer amount;

}
