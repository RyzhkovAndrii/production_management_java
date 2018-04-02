package ua.com.novopacksv.production.dto.roll;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RollBatchResponse {

    private Long rollTypeId;

    private String creationDate;

    private String readyToUseDate;

    private Integer amount;

}
