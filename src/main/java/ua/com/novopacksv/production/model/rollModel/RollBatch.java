package ua.com.novopacksv.production.model.rollModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.BaseEntity;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RollBatch extends BaseEntity {

    private RollManufactured rollManufactured;

    private Integer manufacturedAmount;

    private Integer usedAmount;

    private Integer leftOverAmount;

    public RollBatch(RollManufactured rollManufactured, Integer manufacturedAmount, Integer usedAmount, Integer leftOverAmount) {
        this.rollManufactured = rollManufactured;
        this.manufacturedAmount = manufacturedAmount;
        this.usedAmount = usedAmount;
        if(usedAmount<=manufacturedAmount) {
            this.leftOverAmount = manufacturedAmount - usedAmount;
        }else this.leftOverAmount = 0;
    }
}
