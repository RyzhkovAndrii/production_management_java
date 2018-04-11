package ua.com.novopacksv.production.dto.roll;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RollOperationRequest {

    private String operationDate;

    private String operationType;

    private String manufacturedDate;

    private Long rollTypeId;

    private Integer rollAmount;

}
