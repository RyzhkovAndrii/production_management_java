package ua.com.novopacksv.production.model.reportModel;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.rollModel.RollType;

@Data
@NoArgsConstructor
public class RollReport {

    private RollType rollType;

    private Integer planAmount;

    private Integer actualAmount;

}
