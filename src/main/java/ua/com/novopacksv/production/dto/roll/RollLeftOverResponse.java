package ua.com.novopacksv.production.dto.roll;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RollLeftOverResponse {

    private String date;

    private Long rollTypeId;

    private Integer amount;

}