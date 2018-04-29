package ua.com.novopacksv.production.dto.roll;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RollBatchResponse {

    private String dateManufactured;

    private Long rollTypeId;

    private Integer manufacturedAmount;

    private Integer usedAmount;

    private Integer leftOverAmount;

    private Boolean readyToUse;

}