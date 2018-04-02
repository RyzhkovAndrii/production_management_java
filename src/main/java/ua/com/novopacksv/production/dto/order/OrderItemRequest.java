package ua.com.novopacksv.production.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {

    private Long productTypeId;

    private Integer amount;

    private Long orderId;

}
