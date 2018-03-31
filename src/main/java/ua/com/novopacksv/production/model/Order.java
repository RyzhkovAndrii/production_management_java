package ua.com.novopacksv.production.model;

import java.util.Date;
import java.util.List;

public class Order {

    private Long id;
    private Client client;
    private List<OrderItem> orderItems;
    private Boolean isImportant;
    private Date creationDate;
    private Date deliveryDate;
}
