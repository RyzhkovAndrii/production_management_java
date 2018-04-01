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
@Table(name = "order_item")
public class OrderItem extends BaseEntity{

    @OneToOne
    @PrimaryKeyJoinColumn
    private ProductType product;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private Order order;
}
