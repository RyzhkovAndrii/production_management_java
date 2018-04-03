package ua.com.novopacksv.production.dto.roll;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.dto.BaseEntityResponse;

@Getter
@Setter
public class RollBatchResponse extends BaseEntityResponse {

    private Long rollTypeId;

    private String creationDate;

    private String readyToUseDate;

    private Integer amount;

}
