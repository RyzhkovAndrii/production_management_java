package ua.com.novopacksv.production.dto.order;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ClientRequest {

    @NotBlank(message = "client name is a required field!")
    @Size(max = 50, message = "client name must be less then 50 symbols long!")
    private String name;

}