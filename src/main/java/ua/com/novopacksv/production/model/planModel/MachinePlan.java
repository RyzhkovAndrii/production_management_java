package ua.com.novopacksv.production.model.planModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.BaseEntity;
import ua.com.novopacksv.production.model.productModel.ProductType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "machine_plan")
public class MachinePlan extends BaseEntity {

    @Column(name = "machine_number", nullable = false)
    private Integer machineNumber;

    @Column(name = "time_start", nullable = false)
    private LocalDateTime timeStart;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private ProductType productType;

    @Column(name = "product_amount", nullable = false)
    private Integer productAmount;
}
