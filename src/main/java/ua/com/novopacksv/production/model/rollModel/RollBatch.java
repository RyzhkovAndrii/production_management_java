package ua.com.novopacksv.production.model.rollModel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RollBatch {

    private RollManufactured rollManufactured;

    private Integer manufacturedAmount;

    private Integer usedAmount;

    public Integer getLeftOverAmount() {
        return manufacturedAmount - usedAmount;
    }
}
