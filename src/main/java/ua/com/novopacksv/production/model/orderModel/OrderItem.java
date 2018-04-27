package ua.com.novopacksv.production.model.orderModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.BaseEntity;
import ua.com.novopacksv.production.model.productModel.ProductType;

import javax.persistence.*;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "order_item")
public class OrderItem extends BaseEntity {

    @ManyToOne
    @PrimaryKeyJoinColumn
    private ProductType product;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private Order order;

}