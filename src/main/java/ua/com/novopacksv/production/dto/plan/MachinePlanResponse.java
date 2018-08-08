package ua.com.novopacksv.production.dto.plan;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.dto.BaseEntityResponse;

@Getter
@Setter
public class MachinePlanResponse extends BaseEntityResponse{

    private Integer machineNumber;

    private String timeStart;

    private Long productTypeId;

    private Integer productAmount;

    private String duration;

    private Boolean isImportant;
}
