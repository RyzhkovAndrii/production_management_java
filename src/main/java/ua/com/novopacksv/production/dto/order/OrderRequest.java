package ua.com.novopacksv.production.dto.order;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.model.orderModel.Client;
import ua.com.novopacksv.production.validator.ExistInDb;
import ua.com.novopacksv.production.validator.FutureOrPresent;
import ua.com.novopacksv.production.validator.LocalDateFormat;
import ua.com.novopacksv.production.validator.PastOrPresent;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderRequest {

    @NotNull(message = "client id is a required field!")
    @ExistInDb(value = Client.class, message = "there are no clients with this id!")
    private Long clientId;

    @NotBlank(message = "city is a required field!")
    private String city;

    @NotBlank(message = "delivery date is a required field!")
    @LocalDateFormat(message = "incorrect delivery date format!")
    @FutureOrPresent(message = "delivery date can not be in past!")
    private String deliveryDate;

    @NotNull(message = "is_important is a required field!")
    private Boolean isImportant;

    @LocalDateFormat(message = "incorrect delivery date format!")
    @PastOrPresent(message = "actual delivery date can not be in future!")
    private String actualDeliveryDate;

}
