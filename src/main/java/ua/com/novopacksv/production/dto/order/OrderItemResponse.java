package ua.com.novopacksv.production.dto.order;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.dto.BaseEntityResponse;

@Getter
@Setter
public class OrderItemResponse extends BaseEntityResponse {

    private Long productTypeId;

    private Integer amount;

    private Long orderId;

}
