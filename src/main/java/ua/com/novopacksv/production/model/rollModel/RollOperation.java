package ua.com.novopacksv.production.model.rollModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import ua.com.novopacksv.production.model.BaseEntity;
import ua.com.novopacksv.production.model.productModel.ProductType;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "roll_operation")
public class RollOperation extends BaseEntity {

    @Column(name = "operation_date", nullable = false)
    private LocalDate operationDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @PrimaryKeyJoinColumn
    private RollManufactured rollManufactured;

    @Column(name = "operation_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @Column(name = "roll_amount", nullable = false)
    private Integer rollAmount;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @PrimaryKeyJoinColumn
    @Nullable
    private ProductType productTypeForUseOperation;
}
