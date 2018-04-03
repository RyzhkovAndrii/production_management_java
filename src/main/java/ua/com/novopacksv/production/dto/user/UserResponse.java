package ua.com.novopacksv.production.dto.user;

import lombok.Getter;
import lombok.Setter;
import ua.com.novopacksv.production.dto.BaseEntityResponse;

import java.util.List;

@Getter
@Setter
public class UserResponse extends BaseEntityResponse {

    private String userName;

    private String firstName;

    private String lastName;

    private List<String> roles;

}
