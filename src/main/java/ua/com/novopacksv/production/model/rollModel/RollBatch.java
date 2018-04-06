package ua.com.novopacksv.production.model.rollModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.BaseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RollBatch extends BaseEntity {

    private RollManufactured rollManufactured;

    private Integer manufacturedAmount;

    private Integer usedAmount;

    private Integer leftOverAmount = countLeftOverAmount(manufacturedAmount, usedAmount);

    private Integer countLeftOverAmount(Integer manufacturedAmount, Integer usedAmount){
        if(usedAmount<= manufacturedAmount){
            return manufacturedAmount-usedAmount;
        }
        return 0;
    }
}
