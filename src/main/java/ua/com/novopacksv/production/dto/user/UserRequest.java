package ua.com.novopacksv.production.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequest {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private List<String> roles;

}
