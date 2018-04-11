package ua.com.novopacksv.production.dto.roll;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RollTypeRequest {

    private String name;

    private Double thickness;

    private Double weight;

    private String colorCode;

}
