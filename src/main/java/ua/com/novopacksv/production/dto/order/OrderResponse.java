package ua.com.novopacksv.production.dto.order;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.dto.BaseEntityResponse;

@Getter
@Setter
public class OrderResponse extends BaseEntityResponse {

    private Long clientId;

    private Boolean isImportant;

    private String deliveryDate;

}
