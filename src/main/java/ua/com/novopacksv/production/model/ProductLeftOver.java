package ua.com.novopacksv.production.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "product_left_over")
public class ProductLeftOver extends BaseEntity{

    @OneToOne
    @PrimaryKeyJoinColumn
    private ProductType productType;

    @Column(name = "amount")
    private Integer amount;
}
