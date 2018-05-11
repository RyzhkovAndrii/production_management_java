package ua.com.novopacksv.production.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCheckResponse {

    private Long id;

    private Long productTypeId;

    private String productLeftOverCheckStatus;
}
