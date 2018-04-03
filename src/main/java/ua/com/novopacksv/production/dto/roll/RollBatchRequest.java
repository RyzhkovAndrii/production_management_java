package ua.com.novopacksv.production.dto.roll;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RollBatchRequest {

    private Long rollTypeId;

    private String creationDate;

    private Integer amount;

}
