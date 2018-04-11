package ua.com.novopacksv.production.dto.roll;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.dto.BaseEntityResponse;

@Getter
@Setter
public class RollTypeResponse extends BaseEntityResponse {

    private String name;

    private Double thickness;

    private Double weight;

    private String colorCode;

}
