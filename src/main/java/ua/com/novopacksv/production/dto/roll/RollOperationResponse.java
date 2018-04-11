package ua.com.novopacksv.production.dto.roll;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.dto.BaseEntityResponse;

@Getter
@Setter
public class RollOperationResponse extends BaseEntityResponse {

    private String operationDate;

    private String operationType;

    private String manufacturedDate;

    private Long rollTypeId;

    private Integer rollAmount;

}
