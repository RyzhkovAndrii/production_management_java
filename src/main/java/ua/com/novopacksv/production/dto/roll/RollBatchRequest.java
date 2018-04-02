package ua.com.novopacksv.production.dto.roll;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RollBatchRequest {

    private Long rollTypeId;

    private Date creationDate;

    private Integer amount;

}
