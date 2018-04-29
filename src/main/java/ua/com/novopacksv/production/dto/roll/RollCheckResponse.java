package ua.com.novopacksv.production.dto.roll;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RollCheckResponse {

    private Long id;

    private Long rollTypeId;

    private String rollLeftOverCheckStatus;

}