package ua.com.novopacksv.production.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    private Long clientId;

    private Boolean isImportant;

    private String deliveryDate;

}
