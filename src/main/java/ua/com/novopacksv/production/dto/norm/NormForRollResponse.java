package ua.com.novopacksv.production.dto.norm;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.model.rollModel.RollType;

import java.util.List;

@Getter
@Setter
public class NormForRollResponse {

    private List<RollType> rollTypes;

    private Long productTypeId;

    private Integer norm;

    private Integer normForDay;
}
