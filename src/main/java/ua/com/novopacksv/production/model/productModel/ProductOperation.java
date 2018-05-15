package ua.com.novopacksv.production.model.productModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "product_operation")
public class ProductOperation extends BaseEntity {

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private ProductType productType;

    @Column(columnDefinition = "product_operation_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductOperationType productOperationType;

    @Column(name = "amount", nullable = false)
    private Integer amount;
}
