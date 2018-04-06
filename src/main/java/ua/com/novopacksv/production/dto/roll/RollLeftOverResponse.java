package ua.com.novopacksv.production.dto.roll;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.dto.BaseEntityResponse;

@Getter
@Setter
public class RollLeftOverResponse extends BaseEntityResponse {

    private String date;

    private Long rollTypeId;

    private Integer amount;

}