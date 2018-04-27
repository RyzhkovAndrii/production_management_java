package ua.com.novopacksv.production.model.orderModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import ua.com.novopacksv.production.model.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "order")
public class Order extends BaseEntity {

    @ManyToOne
    @PrimaryKeyJoinColumn
    private Client client;

    @OneToMany(mappedBy = "order", orphanRemoval = true)
    private List<OrderItem> orderItems;

    @Column(name = "is_important", nullable = false)
    private Boolean isImportant;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }

}