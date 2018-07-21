package ua.com.novopacksv.production.dto.user;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.model.userModel.Role;
import ua.com.novopacksv.production.validator.EnumValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "username is a required field!")
    @Size(max = 30, message = "username must be less then 30 symbols long!")
    private String username;

    @NotBlank(message = "password is a required field!")
    @Size(max = 30, message = "password must be less then 30 symbols long!")
    private String password;

    @NotBlank(message = "first name is a required field!")
    @Size(max = 30, message = "first name must be less then 30 symbols long!")
    private String firstName;

    @NotBlank(message = "last name is a required field!")
    @Size(max = 30, message = "last name must be less then 30 symbols long!")
    private String lastName;

    @NotEmpty(message = "roles is a required field!")
    private List<@EnumValue(value = Role.class, message = "role is not found!") String> roles;

}